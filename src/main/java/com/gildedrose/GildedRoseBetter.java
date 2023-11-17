/**
 * Java Gilded Rose kata
 * GildedRoseBetter.java impements GildedRose.java in a clean and more efficient way
 */

package com.gildedrose;

public class GildedRoseBetter {
    Item[] items;

    /**
     * Setter for items field in GildedRoseBetter
     * @param items
     */
    public GildedRoseBetter(Item[] items) {
        this.items = items;
    }

    /**
     * Updates the state of each item in GildedRose
     * Simualtes a day passing
     */
    public void updateQuality() {
        for (Item item: items) {
            rezolveQuality(item);
        }
    }

    /**
     * Rezolves the queslity of each item
     * Increases or deacreases quality
     * Decreases SellIn value
     * @param item
     */
    public void rezolveQuality(Item item) {

        if (isAged(item)) {
            resolveBrieCase(item);
        } else if (isTicket(item)) {
            resolveTicketCase(item);
        } else if (item.equals("Sulfuras, Hand of Ragnaros")) {

        } else {
            resolveDefaultCase(item);
        }
        increaseDay(item);
    }

    /**
     * same as normal method however implemets hard coding
     * @param item
     */
    public void resolveQualityAlternate(Item item) {
        switch (item.name) {
            case "Aged Brie":
                resolveBrieCase(item);
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                resolveTicketCase(item);
                break;
            case "Sulfuras, Hand of Ragnaros":
                break;
            default:
                resolveDefaultCase(item);
                break;
        }
        increaseDay(item);
    }

    /**
     * rezolves for unique brie case
     * @param item
     */
    public static void resolveBrieCase(Item item) {
        if (item.sellIn > 0) {
            increaseQuality(item, 1);
        }else {
            increaseQuality(item, 2);
        }
    }

    /**
     * rezolves for unique ticket case
     * @param item
     */
    public static void resolveTicketCase(Item item) {
        if (item.sellIn >= 0) {
            if (item.sellIn == 0) {
                item.quality = 0;
            } else if (item.sellIn <= 5) {
                increaseQuality(item, 3);
            } else if (item.sellIn <= 10) {
                increaseQuality(item, 2);
                ;
            } else {
                increaseQuality(item, 1);
            }
        }
    }

    /**
     * rezolves for default case
     * @param item
     */
    public static void resolveDefaultCase(Item item) {
        if (isConjured(item)) {
            if (item.sellIn <= 0) {
                decreaseQuality(item, 4);
            }else {
                decreaseQuality(item, 2);
            }
        }else {
            if (item.sellIn <= 0) {
                decreaseQuality(item, 2);
            }else {
                decreaseQuality(item, 1);
            }
        }
    }

    /**
     * Decreases SellIn time expect for the hand of  ragnarock
     * @param item
     */
    public static void increaseDay(Item item) {
        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
            item.sellIn--;
        }
    }

    /**
     * Checks if item is conjured by seporating string and checking
     * if first word is conjured item is conjured
     * @param item
     * @return
     */
    public static boolean isConjured(Item item) {
        return item.name.split(" ")[0].toLowerCase().equals("conjured");
    }

    /**
     * Checks if item is aged by seporating string and checking
     * if first word is aged item is aged
     * @param item
     * @return
     */
    public static boolean isAged(Item item) {
        return item.name.split(" ")[0].toLowerCase().equals("aged");
    }

    /**
     * Checks if item is a ticket by seporating string and checking the regex format
     * if it fits, is a ticket item
     * @param item
     * @return
     */
    public static boolean isTicket(Item item) {
        String name = item.name;
        name.toLowerCase();
        return name.matches("^Backstage passes to a [A-Z0-9]+ concert$");
    }

    /**
     * increases quality to number if its less than 51. else returns 50
     * This is becuase 50 is an upper limit
     * @param item
     * @param increment
     */
    public static void increaseQuality(Item item, int increment){
        int newQuality = 50;
        if (item.quality+increment <= 50) {
            newQuality = item.quality+increment;
        }
        item.quality = newQuality;
    }

    /**
     * decreases quality to number if its more than -1
     * This is because 0 is a lower limit
     * @param item
     * @param decrement
     */
    public static void decreaseQuality(Item item, int decrement){
        int newQuality = 0;
        if (item.quality-decrement >= 0) {
            newQuality = item.quality-decrement;
        }
        item.quality = newQuality;
    }
}
