// Tyvstjålet fra tilfældige folk på nettet som chatgpt har læst om
using System.Numerics;

class Program
{
    static void Main()
    {
        BigInteger x = BigInteger.Parse("1050121080167272443556048214450867363441054732447557600854574323371021993341233414471786819540311637578524782318090701401471864654252154832829029374517109232106237855161080452403809667134710975656071552494599424506891661877234383682734813095252516341");
        int n = 7;
        int precision = 200;

        // Approximate the 7th root by raising x to an integer power
        BigInteger root = BigInteger.Pow(x, 1 / n);

        // Refine the approximation using binary search
        BigInteger upperBound = BigInteger.Pow(10, precision);
        BigInteger lowerBound = BigInteger.Zero;

        while (true)
        {
            // Calculate the midpoint between the upper and lower bounds
            BigInteger mid = (upperBound + lowerBound) / 2;

            // Calculate the nth power of the midpoint
            BigInteger midPower = BigInteger.Pow(mid, n);

            // If the nth power is too large, move the upper bound down
            if (midPower > x)
            {
                upperBound = mid;
            }
            // If the nth power is too small, move the lower bound up
            else if (midPower < x)
            {
                lowerBound = mid;
            }
            // If the nth power is exact, we've found the root
            else
            {
                root = mid;
                break;
            }

            // If the bounds are next to each other, we've reached the desired precision
            if (upperBound - lowerBound <= 1)
            {
                break;
            }
        }
        var bytes = root.ToByteArray();
        Array.Reverse(bytes);
        var str = System.Text.Encoding.UTF8.GetString(bytes);
        System.Console.WriteLine(str);
    }
}