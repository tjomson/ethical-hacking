# Mandatory assignment 1

Thomas Hoffmann Kilbak (thhk)

01/03/2022

## The problem

The problem with having a small `e` like 7, and a big modulo n, such as one that is 4096 bits long, it means that `m^e` is less than n, in which case calculating `m^e % n` will be the same as `m^e`.

This means that to get the original message, we can simply do the reverse operation on the ciphertext to retrieve the original message. That is `c^(1/e)`.

The problem is therefor a combination of the fact that a small e has been chosen, and also a very big n.

## Step-by-step solution

1. Identify that n is longer than c, or that n is longer than `c^(1/e)`.
2. Calculate `a = c^(1/e)`.
3. Convert the number `a` to a byte array with big endian ordering.
4. Decode these bytes to text, yielding `HKN{...}`

## Code to solve

Calculating the 7th root is trivial with small numbers, but to do so for big numbers where accuracy is also required, I can binary search from 0 to n and find the value `a` that satisfies `a^e = n`

A C# program that does this, could like this:
```csharp
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
```

## Other problems with RSA

I am a bit unsure what this part of the exercise is about. I have chosen to solve it, by researching other problems there can be when using a small public exponent.

One such problem is that, if a small e is chosen, then the number of possible d-values that satisfy `de ≡ 1 (mod ϕ(n))` are lower. This makes it easier for an attacker to brute force all the possible d-values, and thereby get the private key.