# Mandatory assignment 1

## The problem

The problem with having a small `e` like 7, and a big modulo n, such as one that is 4096 bits long, it means that `m^e` is less than n, in which case calculating `m^e % n` will be the same as `m^e`.

This means that to get the original message, we can simply do the reverse operation on the ciphertext to retrieve the original message. That is `c^(1/e)`.

The problem is therefor a combination of the fact that a small e has been chosen, and also a very big n.

## Step-by-step solution

1. Identify that n is longer than c, or that n is longer than `c^(1/e)`.
2. Calculate `a = c^(1/e)`.
3. Convert the number a to a byte array with big endian ordering.
4. Decode these bytes to text, yielding `HKN{...}`

A python program that does this, could like this:
```python
c = 1050...
e = 7
res = pow(c, 1/e)
bytes = int(res).to_bytes(4096, 'big')
decoded = bytes.decode('utf-8')
print(decoded)
```

## Other problems with RSA

