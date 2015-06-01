

package com.baifendian.utils;

/**
 * 性能统计类
 * 
 * @author Caviler
 * 
 */
public final class TaskPerfomanceUtil
{
    /**
     * 根据目前已经完成的百分比估算剩余时间
     */
    public static long getEstimatedTime(final long nSpentTime, final int nPercent)
    {
        return (nPercent == 0) ? -1 : ((nSpentTime / nPercent) * (10000 - nPercent));
    }

    public static String getEstimatedTimeString(final long nSpentTime, final int nPercent)
    {
        final long nTime = getEstimatedTime(nSpentTime, nPercent);
        return getTimeString(nTime);
    }

  

    public static String getNanoTimeString(final long nNanoTime)
    {
        return getNanoTimeString(nNanoTime, true);
    }

    /**
     * 纳秒
     * 
     * @param nTime
     * @return
     */
    public static String getNanoTimeString(final long nNanoTime, final boolean noNano)
    {
        boolean nano = true;

        final long nNano = nNanoTime % 1000000;
        final long nMillSeconds = (nNanoTime / 1000000) % 1000;
        final long nSeconds = (nNanoTime / 1000000000) % 60;
        final long nMinutes = (nNanoTime / 1000000000 / 60);

        String s = "Unknown";

        if (nNanoTime != -1)
        {
            s = "";

            if (nMinutes > 0)
            {
                s += Long.toString(nMinutes) + "m";
                nano = false;
            }

            if (nSeconds > 0)
            {
                s += Long.toString(nSeconds) + "s";
                nano = false;
            }

            if (noNano || nMillSeconds > 0)
            {
                final long precision = (nNano + 9 * 1000) / 10000; // 向上所舍入两位小数
                if (nNano > 0 && precision > 0)
                {
                    String temp = "0" + Long.toString(precision);
                    temp = temp.substring(temp.length() - 2);
                    s += Long.toString((nMillSeconds * 100 + precision) / 100) + "." + temp + "ms";
                }
                else
                {
                    s += Long.toString(nMillSeconds) + "ms";
                }
                nano = false;
            }

            if (nano && nNano > 0)
            {
                s += Long.toString(nNano) + "ns";
            }
        }

        return s;
    }

    public static int getPercent(final long nTotal, final long nDoneCount)
    {
        return (int) ((nDoneCount * 100.0 / nTotal));
    }

    public static String getPercentString(final long nTotal, final long nDoneCount)
    {
        if (nTotal == 0)
        {
            return "-";
        }
        final int nPercent = getPercent(nTotal, nDoneCount);

        return Double.toString(nPercent / 100.0);
    }

    /**
     * 返回每分钟处理的任务数
     */
    public static String getSpeedPerMinuteString(final long nSpentTime, final long nDoneCount)
    {
        return ((0 == nSpentTime) ? 0 : ((long) (nDoneCount / (nSpentTime / 60.0 / 1000.0)))) + "/m";
    }

    

    /**
     * 返回每秒处理的任务数
     */
    public static String getSpeedPerSecondString(final long nSpentTime, final long nDoneCount)
    {
        return ((0 == nSpentTime) ? 0 : ((long) (nDoneCount / (nSpentTime / 1000.0)))) + "/s";
    }

  

    /**
     * 精确到分,不带秒和毫秒
     * 
     * @param nTime
     *            毫秒
     * @return
     */
    public static String getTimeNoSecondString(final long nTime)
    {
        final long nMinutes = (nTime / 1000 / 60) % 60;
        final long nHours = (nTime / 1000 / 60 / 60);

        String s = "Unknown";

        if (nTime != -1)
        {
            s = "";
            if (nHours > 0)
            {
                s += Long.toString(nHours) + "h";
            }

            if (nMinutes > 0)
            {
                s += Long.toString(nMinutes) + "m";
            }
        }

        return s;
    }

    /**
     * 毫秒
     * 
     * @param nTime
     * @return
     */
    public static String getTimeString(final long nTime)
    {
        final long nMillSeconds = nTime % 1000;
        final long nSeconds = (nTime / 1000) % 60;
        final long nMinutes = (nTime / 1000 / 60) % 60;
        final long nHours = (nTime / 1000 / 60 / 60);

        String s = "Unknown";

        if (nTime != -1)
        {
            s = "";
            if (nHours > 0)
            {
                s += Long.toString(nHours) + "h";
            }

            if (nMinutes > 0)
            {
                s += Long.toString(nMinutes) + "m";
            }

            if (nSeconds > 0)
            {
                s += Long.toString(nSeconds) + "s";
            }

            if (nMillSeconds > 0 || 0 == nTime)
            {
                s += Long.toString(nMillSeconds) + "ms";
            }
        }

        return s;
    }

    /**
     * @param nTime
     * @return
     */
    public static String getTimeString_ZH(final long nTime)
    {
        if (nTime < 1000)
        {
            return nTime + "毫秒";
        }

        final int seconds = (int) (nTime / 1000);

        int n = seconds;
        final int h = (n / (60 * 60)); // 时
        n %= (60 * 60);
        final int m = n / 60; // 分
        n %= 60;
        final int s = n; // 秒

        String sh = Integer.toString(h);
        if (sh.length() < 2)
        {
            if (h > 0)
            {
                sh = "0" + sh;
            }
        }
        String sm = (Integer.toString(m));
        if (sm.length() < 2)
        {
            if (m > 0)
            {
                sm = "0" + sm;
            }
        }

        String ss = (Integer.toString(s));
        if (ss.length() < 2)
        {
            if (s > 0)
            {
                ss = "0" + ss;
            }
        }

        String sTime = "";
        if (h > 0)
        {
            sTime += (sh + "时" + sm + "分" + ss + "秒");
        }
        else
        {
            if (m > 0)
            {
                sTime += (m + "分" + ss + "秒");
            }
            else
            {
                sTime += s + "秒";
            }
        }

        return sTime;
    }

    private TaskPerfomanceUtil()
    {
    }

}
