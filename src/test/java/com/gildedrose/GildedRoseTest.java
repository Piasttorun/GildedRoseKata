package com.gildedrose;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GildedRoseTest {

    static GildedRose app1;
    static GildedRoseBetter app2;

    static void set() {
        Item[] items = new Item[] {
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6) };

        app1 = new GildedRose(items);
        app2 = new GildedRoseBetter(items);
    }

    @BeforeAll
    static void setUpAll() {
        set();
    }


    @RepeatedTest(30)
    @DisplayName("Tests the results after each day of sales")
    void testDay1() {
        app1.updateQuality();
        app2.updateQuality();
        for (int i = 0; i < app1.items.length; i++) {
            Assertions.assertEquals(app1.items[i], app2.items[i]);
        }
        System.out.println(Arrays.toString(app1.items));
        System.out.println(Arrays.toString(app2.items));
    }

    @Test
    @DisplayName("Tests correct case of conjured")
    void testIsConjuredTrue() {
        Item testItem = new Item("Conjured stamina potion", 10, 20);
        Assertions.assertEquals(true, GildedRoseBetter.isConjured(testItem));
    }

    @Test
    @DisplayName("Tests false case of conjured")
    void testIsConjuredFalse() {
        Item testItem = new Item("stamina potion", 10, 20);
        Assertions.assertEquals(false, GildedRoseBetter.isConjured(testItem));
    }

    @Test
    @DisplayName("Tests correct case of aged")
    void testIsAgedTrue() {
        Item testItem = new Item("Aged stamina potion", 10, 20);
        Assertions.assertEquals(true, GildedRoseBetter.isAged(testItem));
    }

    @Test
    @DisplayName("Tests false case of aged")
    void testIsAgedFalse() {
        Item testItem = new Item("stamina potion", 10, 20);
        Assertions.assertEquals(false, GildedRoseBetter.isAged(testItem));
    }

    @Test
    @DisplayName("Tests correct case of ticket")
    void testIsTicketTrue() {
        Item testItem = new Item("Backstage passes to a EWRGHW66WEG8W8EG concert", 10, 20);
        Assertions.assertEquals(true, GildedRoseBetter.isTicket(testItem));
    }

    @Test
    @DisplayName("Tests false case of ticket")
    void testIsTicketFalse() {
        Item testItem = new Item("stamina potion", 10, 20);
        Assertions.assertEquals(false, GildedRoseBetter.isTicket(testItem));
    }

    @Test
    @DisplayName("Tests normal case of increasing quality")
    void testIncreaseQuality() {
        Item testItem = new Item("stamina potion", 10, 40);
        GildedRoseBetter.increaseQuality(testItem,3);
        Assertions.assertEquals(43, testItem.quality);
    }

    @Test
    @DisplayName("Tests max value increase quality case")
    void testIncreaseQualityBound() {
        Item testItem = new Item("stamina potion", 10, 50);
        GildedRoseBetter.increaseQuality(testItem,5);
        Assertions.assertEquals(50, testItem.quality);
    }

    @Test
    @DisplayName("Tests normal case of decreasing quality")
    void testDecreaseQuality() {
        Item testItem = new Item("stamina potion", 10, 20);
        GildedRoseBetter.decreaseQuality(testItem,4);
        Assertions.assertEquals(16, testItem.quality);
    }

    @Test
    @DisplayName("Tests max value decrease quality case")
    void testDecreaseQualityBound() {
        Item testItem = new Item("stamina potion", 10, 2);
        GildedRoseBetter.decreaseQuality(testItem,4);
        Assertions.assertEquals(0, testItem.quality);
    }

    @Test
    @DisplayName("Tests decrease in SellIn day value")
    void testIncreaseSellIN() {
        Item testItem = new Item("stamina potion", 10, 2);
        GildedRoseBetter.increaseDay(testItem);
        Assertions.assertEquals(9, testItem.sellIn);
    }

    @Test
    @DisplayName("Tests decrease in SellIn day value")
    void testIncreaseSellINSulfuras() {
        Item testItem = new Item("Sulfuras, Hand of Ragnaros", 10, 80);
        GildedRoseBetter.increaseDay(testItem);
        Assertions.assertEquals(10, testItem.sellIn);
    }

    @Test
    @DisplayName("Tests default quality increase case: not passed sell in, not conjured")
    void testDefaultQualityNotSellInNotConjured() {
        Item testItem = new Item("stamina potion", 10, 15);
        GildedRoseBetter.resolveDefaultCase(testItem);
        Assertions.assertEquals(14, testItem.quality);
    }

    @Test
    @DisplayName("Tests default quality increase case: not passed sell in, not conjured")
    void testDefaultQualitySellInNotConjured() {
        Item testItem = new Item("stamina potion", -5, 15);
        GildedRoseBetter.resolveDefaultCase(testItem);
        Assertions.assertEquals(13, testItem.quality);
    }

    @Test
    @DisplayName("Tests default quality increase case: not passed sell in, conjured")
    void testDefaultQualityNotSellInConjured() {
        Item testItem = new Item("Conjured stamina potion", 10, 15);
        GildedRoseBetter.resolveDefaultCase(testItem);
        Assertions.assertEquals(13, testItem.quality);
    }

    @Test
    @DisplayName("Tests default quality increase case: not passed sell in, conjured")
    void testDefaultQualitySellInConjured() {
        Item testItem = new Item("Conjured stamina potion", -5, 15);
        GildedRoseBetter.resolveDefaultCase(testItem);
        Assertions.assertEquals(11, testItem.quality);
    }

    @Test
    @DisplayName("Tests ticket quality increase case: sell in 2")
    void testTicketQualitySellIn2() {
        Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 2, 20);
        GildedRoseBetter.resolveTicketCase(testItem);
        Assertions.assertEquals(23, testItem.quality);
    }

    @Test
    @DisplayName("Tests ticket quality increase case: sell in 6")
    void testTicketQualitySellIn6() {
        Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20);
        GildedRoseBetter.resolveTicketCase(testItem);
        Assertions.assertEquals(22, testItem.quality);
    }

    @Test
    @DisplayName("Tests ticket quality increase case: sell in 12")
    void testTicketQualitySellIn12() {
        Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 12, 20);
        GildedRoseBetter.resolveTicketCase(testItem);
        Assertions.assertEquals(21, testItem.quality);
    }

    @Test
    @DisplayName("Tests ticket quality increase case: sell in 0")
    void testTicketQualitySellInNegative() {
        Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 30);
        GildedRoseBetter.resolveTicketCase(testItem);
        Assertions.assertEquals(0, testItem.quality);
    }

    @Test
    @DisplayName("Tests brie quality increase case: sell in not passed")
    void testBrieQualityNotSellIn() {
        Item testItem = new Item("Aged Brie", 5, 30);
        GildedRoseBetter.resolveBrieCase(testItem);
        Assertions.assertEquals(31, testItem.quality);
    }

    @Test
    @DisplayName("Tests default quality increase case: sell in passed")
    void testBrieQualitySellIn() {
        Item testItem = new Item("Aged Brie", -3, 30);
        GildedRoseBetter.resolveBrieCase(testItem);
        Assertions.assertEquals(32, testItem.quality);
    }
}
