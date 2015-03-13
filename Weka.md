# Weka #

## TODO ##

  * Wyświetlanie znalezionych reguł w GUI.


## Integracja z GUI i bazą danych ##

Przykład użycia wyszukiwacza reguł Weki z poziomu GUI:
```
// 'this' to aktualne Activity w Androidzie
DbHandler dbHandler = new DbHandler(new AndroidFileIO(this));
Table data = dbHandler.generateDataTable("mentalrate");

RulesFinder finder = new RulesFinder(data);
Iterator<Rule> rulesIter = finder.findRules().iterator();

while (rulesIter.hasNext()) {
    String message = RuleTranslator.humanReadable(rulesIter.next());
    // wyświetlamy message użytkownikowi
    // czekamy na kliknięcie...
}
```
Ten przykład był dla mentalrate, przypadki dla moodrate i physicalrate są analogiczne.

Użytkownikowi powinniśmy wyświetlać chyba raczej sugestie niż surowe reguły, więc można zmodyfikować RuleTranslator żeby wyświetlał sugestie w formie:

> I `[`strongly`]` recommend that if your want your `<class_attribute>` to be `<class_attribute_value>`, you should make sure your `<attribute>` is `<attribute_value>` and your `<attribute2>` is `<attribute2_value>` and...

albo coś w tym stylu.


## Struktura tabeli z danymi dla Weki ##

**UWAGA: Struktura danych się zmieniła, niech bazodanowcy uzupełnią...**

przykładowa struktura tabeli do analizy uzgodniona z wykładowcą:
```
[date, activity0name, activity0starthour, activity0duration, activity0intensivity, food0name, food0amount, food0timeconsumed, food1name, food1amount, food1timeconsumed, food2name, food2amount, food2timeconsumed, foodsum0name, foodsum0amount, foodsum1name, foodsum1amount, mentalrate, moodrate, physicalrate]
[10-3-2010, Swimming, 12, 63, High, Potato, 3.0, before 11, , , , , , , Potato, 3.0, , , , , ]
[12-12-2010, Swimming, 12, 63, High, Potato, 3.0, before 11, , , , , , , Potato, 3.0, , , , , ]
[1-1-2011, Swimming, 12, 63, High, Potato, 3.0, before 11, , , , , , , Potato, 3.0, , , , , ]
[22-1-2011, Swimming, 12, 63, High, Potato, 3.0, before 11, Tomato, 2.0, after 17, , , , Potato, 3.0, Tomato, 2.0, , , ]
[27-1-2011, Swimming, 12, 63, High, Potato, 3.0, before 11, Tomato, 2.0, after 17, , , , Potato, 3.0, Tomato, 2.0, , , ]
[29-1-2011, Swimming, 12, 63, High, Potato, 3.0, before 11, Tomato, 2.0, after 17, , , , Potato, 3.0, Tomato, 2.0, Poor, Poor, Poor]
[6-2-2011, Swimming, 12, 63, High, Potato, 3.0, before 11, Potato, 7.0, after 17, Tomato, 2.0, after 17, Potato, 10.0, Tomato, 2.0, Poor, Poor, Poor]
```

date - data wpisu dd-mm-yyyy

X - numer aktywności

activityXname - nazwa

activityXstarthour - godzina rozpoczęcia
etc...

X - numer elementu posiłku

foodXname- nazwa

foodXamount- ilość

Liczba kolumn ulega zmianie w zależności od maksymalnej ilości food i activity we wszystkich wpisach (zalecenia prowadzącego)

Dodatkowo na samym końcu jest podsumowanie łaczne danych składników posiłków:

foodsumXname

foodsumXamount

To na wypadek gdy ktoś zje parę np. ziemniaków przez cały dzień w innych porach a my chcemy mieć ich sumę (także zalecenia prowadzącego).

Można już używać metody `dbHandler.generateDataTable();` do pobrania obiektu klasy Table z danymi z bazy