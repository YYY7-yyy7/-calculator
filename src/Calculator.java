import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            String finalString = calc(input);
            System.out.println(finalString);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String calc(String input){
        String [] arr = input.split(" ");
        if (arr.length > 3){
            throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию");
        }
        if (arr.length < 3){
            throw new IllegalArgumentException("Cтрока не является математической операцией");
        }

        String operation = arr[1];
        String num1 = arr[0];
        String num2 = arr[2];

        boolean isRoman = isRomanNumber(num1) && isRomanNumber(num2);
        boolean isArabic = isArabicNumber(num1) && isArabicNumber(num2);

        if (isRoman && isArabic){
            throw new IllegalArgumentException("Только арабские или римские цифры");
        }

        int result;
        int x,y;
        if (isRoman){
            x = romanArabic(num1);
            y = romanArabic(num2);
        }
        else if (isArabic){
            x = Integer.parseInt(num1);
            y = Integer.parseInt(num2);
        }
        else {
            throw new IllegalArgumentException("Неверный формат чисел");
        }

        if (x < 1 || x > 10 || y < 1 || y > 10) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");
        }

        result = switch (operation) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> {
                if (y == 0) {
                    System.out.println("Деление на ноль");
                }
                yield x / y;
            }
            default -> throw new IllegalArgumentException("Неподдерживаемая операция");
        };
        if (isRoman){
            if(result < 1){
                throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
            }
            return arabicRoman(result);
        }
        else {
            return String.valueOf(result);
        }
    }

    private static boolean isRomanNumber(String num){
        return num.matches("^[IVX]+$");
    }
    private static boolean isArabicNumber(String num){
        return num.matches("^[0-9]+$");
    }

    private static int romanArabic (String roman){
        switch (roman){
            case "I": return 1;
            case "II": return 2;
            case "III": return 3;
            case "IV": return 4;
            case "V": return 5;
            case "VI": return 6;
            case "VII": return 7;
            case "VIII": return 8;
            case "IX": return 9;
            case "X": return 10;
            default: throw new IllegalArgumentException("Неверное римское число");
        }
    }
    private static String arabicRoman(int number){
        if (number < 1){
            throw new IllegalArgumentException("Римские числа не могут быть меньше 1");
        }
        String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        StringBuilder roman = new StringBuilder();
        for( int i = values.length-1; i >=0; i-- ){
            while (number >= values[i]){
                number -= values[i];
                roman.append(romanNumerals[i]);
            }
        }
        return roman.toString();
    }
}

