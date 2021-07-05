package java8.timetest;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * 功能描述：测试Java8的新时间API
 *
 * @author RenShiWei
 * Date: 2020/6/23 20:10
 **/
public class TimeTest {

    @Test
    public void test() {
        //获取系统的时间
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        //根据指定日期/时间创建
        LocalDateTime ldt2 = LocalDateTime.of(2016, 12, 25, 12, 12, 12);
        System.out.println(ldt2);
        //向当前对象添加天、周、月.....   plusXxx()
        LocalDateTime ldt3 = ldt2.plusDays(3).plusMonths(2);
        System.out.println(ldt3);
        //向当前对象减去天、周、月....   minusXxx()
        LocalDateTime ldt4 = ldt2.minusYears(1);
        System.out.println(ldt4);
        //添加一个Duration或者Period   plus()/minus()

        //修改对象指定的月、天、周、年.....  withXxx()
        LocalDateTime ldt5 = ldt2.withDayOfMonth(1);
        System.out.println(ldt5);

        //获取指定时间/日期对象的年月日....  get()
        int year = ldt.getYear();
        System.out.println(year);
    }

    @Test
    public void test2() {
        //获取当前系统时间戳   2020-06-23T12:34:41.555Z(还是时间的格式,默认是UTC时区)
        Instant instant = Instant.now();
        System.out.println(instant);
        //利用偏移量，设置为东八区
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);
        //获取毫秒形式的时间戳值
        System.out.println("秒数" + instant.getEpochSecond());
        System.out.println("毫秒数" + instant.toEpochMilli());
        //指定秒数获取instant实例
        Instant instant1 = Instant.ofEpochSecond(instant.getEpochSecond());
        System.out.println(instant1);
    }

    @Test
    public void test3() {
        Instant ins1 = Instant.now();
        System.out.println("--------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant ins2 = Instant.now();
        System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));

        System.out.println("----------------------------------");
        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2011, 1, 1);
        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());
        System.out.println(pe.getMonths());
        System.out.println(pe.getDays());
    }

    @Test
    public void test4() {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime ldt1 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt + "\n" + ldt1);

        //自定义：下一个工作日
        LocalDateTime ldt3 = ldt.with((l) -> {
            LocalDateTime ldt2 = (LocalDateTime) l;
            DayOfWeek dow = ldt2.getDayOfWeek();
            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt2.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt2.plusDays(2);
            } else {
                return ldt2.plusDays(1);
            }
        });
        System.out.println(ldt3);
    }

    @Test
    public void test5() {
        //DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);
        System.out.println(strDate);

        LocalDateTime newLdt = LocalDateTime.parse("2020-02-03 15:56:23", DateTimeFormatter.ofPattern("yyyy-MM-dd " +
                "HH:mm:ss"));
        System.out.println(newLdt);
    }

    @Test
    public void test6() {
        //获取所有失去的set集合
        Set<String> set = ZoneId.getAvailableZoneIds();
        //ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);

    }

    @Test
    public void test7() {
        //获取系统默认时区 (当前瞬时时间 )
        Clock clock1 = Clock.systemDefaultZone();
        System.out.println("系统时区" + clock1);
        System.out.println("系统时间日期：" + clock1.instant());
        System.out.println("时间毫秒：" + clock1.millis());
        System.out.println(clock1.getZone());
        System.out.println("-------------------");
        //获取系统时钟，并将其转换成使用UTC时区的日期和时间
        Clock clock = Clock.systemUTC();
        System.out.println("时间日期：" + clock.instant());
        System.out.println("时间毫秒值：" + clock.millis());
        System.out.println("-------------");
        Clock clock2 = Clock.system(ZoneId.of("Asia/Shanghai"));
        System.out.println(clock2);
    }


}

