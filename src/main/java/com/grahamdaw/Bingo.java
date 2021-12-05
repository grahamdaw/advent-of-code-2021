package com.grahamdaw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Bingo {
    private List<Card> cards = new ArrayList<>();
    private List<String> protoCard = new ArrayList<>();
    private Card winningCard = null;
    private List<Card> bingoedCards = new ArrayList<>();

    public void addRowToCurrentCard(String line) {
        protoCard.add(line);
    }

    public void saveCurrentCardAndReset() {
        Card card = new Card(protoCard);
        cards.add(card);
        // Wipe current card & start again
        protoCard = new ArrayList<>();
    }

    public void drawBingoNumberPart1(Integer num) {
        // Remove number for all cards and check if there
        // is a winner.
        int numCards = cards.size();
        for (int i = 0; i < numCards; i++) {
            Card card = cards.get(i);
            card.removeNumber(num);
            // card.printCard();
            if (card.isBingo()) {
                // For part 1 of this puzzle, we should then stop what we are doing. We can
                // still keep the winning card
                    winningCard = card;
                    break;
            }
        }
    }

    public void drawBingoNumberPart2(Integer num) {
        // Remove number for all cards
        int numCards = cards.size();
        for (int i = 0; i < numCards; i++) {
            Card card = cards.get(i);
            // This is inefficient
            if (!card.isBingo()) {
                card.removeNumber(num);
                if (card.isBingo()) {
                    bingoedCards.add(card);
                }
            }
        }
    }

    public boolean isWinner() {
        return winningCard != null;
    }

    public boolean isRemainingCards() {
        return bingoedCards.size() < cards.size();
    }

    public int winningScore(Integer lastNumberDrawn) {
        if (isWinner()) {
            winningCard.printCard();
        }
        // Get card score
        Integer cardScore = winningCard.getScore();
        // Multiply by last number
        return lastNumberDrawn * cardScore;
    }

    public int lastCardScore(Integer lastNumberDrawn) {
        // Get card score
        Integer cardScore = bingoedCards.get(bingoedCards.size()-1).getScore();
        // Multiply by last number
        return lastNumberDrawn * cardScore;
    }
}


class Card {

    private List<List<Integer>> rows = new ArrayList();
    private List<List<Integer>> columns = new ArrayList();
    private boolean isBingo = false;

    public Card(List<String> initialRows) {
        // Split each row by whitespace the parseInt for each value
        initialRows.forEach((String row) -> {
            String[] splitRow = row.strip().split("\\s+");
            List<Integer> parsedRow = Arrays.asList(splitRow).stream().map(Integer::parseInt).collect(Collectors.toList());
            rows.add(parsedRow);
        });
        // Next build card columns from rows
        for (int i = 0; i < rows.get(0).size(); i++) {
            columns.add(rowsToColumn(i));
        }
    }

    private List<Integer> rowsToColumn(int index) {
        List<Integer> column = new ArrayList<>();
        // Loop all rows at index value to get
        rows.stream().forEach((row) -> {
            column.add(row.get(index));
        });
        return column;
    }

    public boolean isBingo() {
        boolean won = false;
        // Check rows
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).size() == 0) {
                return true;
            }
        }
        // Check columns
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).size() == 0) {
                return true;
            }
        }
        return false;
    }

    public void removeNumber(Integer number) {
        // Remove from rows
        for (int i = 0; i < rows.size(); i++) {
            List<Integer> row = rows.get(i);
            row.remove(number);
            rows.set(i, row);
        }
        // remove from columns
        for (int i = 0; i < columns.size(); i++) {
            List<Integer> column = columns.get(i);
            column.remove(number);
            columns.set(i, column);
        }
    }

    public Integer getScore() {
        // Get the sum on all remaining rows (columns are only recorded for the purposes of determining a winner)
        Integer score = 0;
        for (int i = 0; i < rows.size(); i++) {
            List<Integer> row = rows.get(i);
            Integer rowScore = row.stream().mapToInt(Integer::intValue).sum();
            score += rowScore;
        }
        return score;
    }

    public void printCard() {
        System.out.println(rows);
        System.out.println(columns);
    }
}