## Idea ##

Dostarczenie użytkownikowi programu do prowadzenia dziennika codziennych aktywności, przyjmowanych posiłków i leków, oraz samopoczucia. Po czasie taki dziennik byłby automatycznie analizowany w celu wykrycia regularności w samopoczuciu - w zależności od rożnych aktywności i spożycia. Wiedza ta mogłaby być użyta do zindywidualizowanej modyfikacji zachowania w celu poprawy samopoczucia. Np. użytkownik przed ważnym egzaminem mógłby skorzystać z rekomendacji, które prawdopodobnie prowadzą do jego zwiększonej sprawności intelektualnej, czy odkryć że po pewnych pokarmach czuje się źle.

Dodatkowo, powstały dziennik dostarczałby informacje o nawykach, które mogą być użyteczne w diagnozie lekarskiej. Nawyki te, jak choćby sposób odżywiania się, mogłyby też być zestawione z bazami danych wartości odżywczych, w wyniku czego użytkownik mógłby otrzymać analizę swojego sposobu odżywiania się i ew. sugestie - dostosowane do jego aktualnego spożycia - jak zdrowotnie zmodyfikować dietę.

Im dłuższy czas prowadzenia takiego dziennika tym więcej zależności i rekomendacji dałoby się stworzyć. Niektóre mogą być trudne do ustalenia innymi sposobami, np. użytkownik może być nieświadomy, że źle toleruje niektóre pokarmy i że powodują one swędzenie skóry. Dziennik obejmujący okresy gdy swędzenie to występowało i nie, jak i gdy spożywał te pokarmy, jak i nie, może pozwolić automatycznemu systemowi wychwycić tę zależność.

## Zapotrzebowanie ##

Badania wykazują, że wiele - wcześniej uznawanych za losowe - aspektów zdrowia i samopoczucia jest konsekwencją czynników, na które mamy wpływ. Z racji na indywidualne różnice - zarówno w sposobie życia, jak i reagowania na czynniki - ogólne rekomendacje, np. nie pić kawy po kolacji by dobrze spać, nie zawsze dają zadowalające wyniki w przypadku konkretnej osoby - która może w ogóle nie pić kawy, lub też być na nią niewrażliwa, a dominujący czynnik wpływający na jakość jej snu może być zupełnie inny - być może łatwy do kontroli, gdyby się go poznało.

Wiele osób funkcjonuje obecnie 'na wysokich obrotach' - dużo pracuje, niewłaściwie się odżywia, chcąc poprawić samopoczucie sięga po używki, nie mając jednocześnie czasu i wiedzy by zdać sobie sprawę z konsekwencji, jak i potencjału zmian. Prowadzi to do nieoptymalnego samopoczucia - np. zmęczenia - które jest 'leczone' kolejnymi używkami, co może pogarszać problem.

Proponowany system unaoczniłby konsekwencje zarówno zachowań źle wpływających na samopoczucie, jak i zaproponował konkretnie te które dobrze działają na daną osobę. Coraz dłuższe prowadzenie dziennika dawałoby też coraz większe możliwości analiz, sugestii, rekomendacji - tym samym motywując do kontynuacji.

## Realizacja ##

System składałby się z kilku modułów
  1. Dziennik - pozwalający na łatwe wprowadzenie informacji o aktywnościach i samopoczuciu
  1. Odkrywca - algorytm próbujący znaleźć w danych z dziennika statystycznie znaczące regularności
  1. Interfejs danych - przyłączenie specjalizowanych baz danych, np. n/t wartości odżywczych składników odnotowanych w Dzienniku
  1. Importer/exporter danych

Dziennik do wprowadzania danych umożliwiałby zarówno definiowanie własnych pozycji, np. 'Przyjęcie leku XY', jak i sugerowałby pewne stałe pola, np. odnośnie posiłków, czy ilości wypalonych papierosów.

## Rozszerzenia ##

Proponowany system, początkowo służący do zbierania informacji, w miarę ich gromadzenia umożliwiałby szerszą funkcjonalność. Powiązane byłoby to z tworzeniem specjalizowanych baz danych - np. zawierających nie tylko wartości odżywcze pokarmów, ale i choćby przepisy kulinarne z tymi składnikami, których niedobór można wywnioskować z Dziennika. Tak więc możliwe poszerzenia to:
  1. Różne bazy danych
  1. Usprawnienia algorytmu wnioskującego Odkrywca
  1. Różne sposoby wizualizacji danych z Dziennika
  1. Połączenie z Terminarzem przypominającym o planowanych/cyklicznych działaniach, np. basenie w piątki, czy przyjęciu leku XY
  1. Połączenie z lokalną informacja n/t serwisów które mogą wynikać z Dziennika, np. gdyby Odkrywca wywnioskował możliwość uczulenia pokarmowego, mogłaby zostać zaprezentowana lokalna lista lekarzy alergologów

## Idea summary ##

To develop software allowing a user to log daily activities, foods and how s/he felt, mentally and physically. Then to automaticly find patterns leading to the experienced feel. They could suggest causes of good or bad performance, even medical problems, e.g. allergy. This could lead to personal improvement, helped by guides added later.


---


Copyright dr Stefan Zemke, steze@wp.pl
Wersja 2009.02.26