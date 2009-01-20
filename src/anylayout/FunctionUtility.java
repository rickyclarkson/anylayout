package anylayout;

import fj.F;
import fj.data.Option;

public final class FunctionUtility
{
    public static <T, R> R constIfNothing(Option<T> o, R or, F<T, R> f)
    {
        return o.option(or, f);
    }

    public static <T> F<T, Integer> minus(final F<T, Integer> f, final F<T, Integer> g)
    {
        return new F<T, Integer>()
        {
            public Integer f(T t)
            {
                return f.f(t)-g.f(t);
            }
        };
    }
}