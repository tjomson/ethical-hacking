using System.Numerics;

class Program
{
    static void Main()
    {
        BigInteger n = BigInteger.Parse("1050121080167272443556048214450867363441054732447557600854574323371021993341233414471786819540311637578524782318090701401471864654252154832829029374517109232106237855161080452403809667134710975656071552494599424506891661877234383682734813095252516341");
        int e = 7;

        BigInteger root = BigInteger.Pow(n, 1 / e);

        BigInteger high = n;
        BigInteger low = 0;

        while (high > low)
        {
            BigInteger mid = (high + low) / 2;
            BigInteger midPower = BigInteger.Pow(mid, e);
            if (midPower > n)
                high = mid;
            else if (midPower < n)
                low = mid;
            else
            {
                root = mid;
                break;
            }
        }
        var bytes = root.ToByteArray();
        Array.Reverse(bytes);
        var str = System.Text.Encoding.UTF8.GetString(bytes);
        System.Console.WriteLine(str);
    }
}
