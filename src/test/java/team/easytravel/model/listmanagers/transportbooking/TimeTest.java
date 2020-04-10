package team.easytravel.model.listmanagers.transportbooking;

class TimeTest {

    // TODO: Redo test for Time
    //@Test
    //public void constructorNullThrowsNullPointerException() {
    //    assertThrows(NullPointerException.class , () -> new Time(null));
    //}
    //
    //@Test
    //public void constructorInvalidTimeThrowsDateTimeParseException() {
    //    // Dont have 2016, Month 13, Year 13
    //    String now = "2016-13-13 10:30";
    //    assertThrows(DateTimeParseException.class , ()-> LocalDateTime.parse(now));
    //}
    //
    //
    //@Test
    //public void compareTo() {
    //    String now = "2016-05-05 10:30";
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //    LocalDateTime localDateTime = LocalDateTime.parse(now , formatter);
    //    Time time = new Time(localDateTime);
    //    LocalDateTime comparedLocalDateTime = LocalDateTime.parse("2016-05-05 10:30", formatter);
    //    Time comparedTime = new Time(comparedLocalDateTime);
    //
    //    // If the times are the same
    //    assertEquals(0 , time.compareTo(comparedTime));
    //
    //    //Earlier time
    //    LocalDateTime earlierDateTime = LocalDateTime.parse("2014-05-05 10:30", formatter);
    //    Time earlierTime = new Time(earlierDateTime);
    //
    //    // 2 years difference
    //    assertEquals(2, time.compareTo(earlierTime));
    //
    //    // 1 year later
    //    LocalDateTime futureDateTime = LocalDateTime.parse("2017-05-05 10:30", formatter);
    //    Time futureTime = new Time(futureDateTime);
    //    assertEquals(-1, time.compareTo(futureTime));
    //
    //}
    //
    //@Test
    //public void testToString() {
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //    LocalDateTime futureDateTime = LocalDateTime.parse("2017-05-05 10:30", formatter);
    //    Time futureTime = new Time(futureDateTime);
    //    //TODO Ask boss how he want to do with this to string
    //    assertEquals("2017-05-05T10:30", futureTime.toString());
    //
    //}
    //
    //@Test
    //public void testEquals() {
    //    String now = "2016-05-05 10:30";
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //    LocalDateTime localDateTime = LocalDateTime.parse(now, formatter);
    //    Time time = new Time(localDateTime);
    //    LocalDateTime comparedLocalDateTime = LocalDateTime.parse("2016-05-05 10:30", formatter);
    //    Time comparedTime = new Time(comparedLocalDateTime);
    //    assertEquals(time, comparedTime);
    //}
    //
    //@Test
    //public void testHashCode() {
    //    String now = "2016-05-05 10:30";
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //    LocalDateTime localDateTime = LocalDateTime.parse(now, formatter);
    //    Time time = new Time(localDateTime);
    //    LocalDateTime comparedLocalDateTime = LocalDateTime.parse("2016-05-05 10:30", formatter);
    //    Time comparedTime = new Time(comparedLocalDateTime);
    //    assertEquals(time.hashCode(), comparedTime.hashCode());
    //}
}
