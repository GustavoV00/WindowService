package com.example.helloworld.windowService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Time {
    private int seconds;
    private int minutes;
    private int hours;
    private int day;
    private int month;
    private int year;

    public Time() {
        Instant timestamp = Instant.now();
        LocalDateTime dateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        this.seconds = dateTime.getSecond();
        this.minutes = dateTime.getMinute();
        this.hours = dateTime.getHour();
        this.day = dateTime.getDayOfMonth();
        this.month = dateTime.getMonthValue();
        this.year = dateTime.getYear();
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d %02d:%02d:%02d", day, month, year, hours, minutes, seconds);
    }
}
