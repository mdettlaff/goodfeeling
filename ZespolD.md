Skład zespołu D:
  1. Tomasz Dabulis
  1. Michał Dettlaff
  1. Łukasz Draba
  1. Dariusz Kuziemski
  1. Karolina Steinborn

## Bieżące zadania ##

Aktualnym celem jest stworzenie zintegrowanego testu, będącego połączeniem gry Balloons oraz testu samopoczucia. Gra Balloons pochodzi z Brain Test Britain ([link](https://www.bbc.co.uk/labuk/results/braintestbritain/games/balloons.html)), więc nie będziemy jej tu szczegółowo opisywać. Test samopoczucia polega na pokazywaniu użytkownikowi par obrazów, gdzie z każdej pary ma wybrać jeden bądź żaden. Obrazy należą do określonych kategorii związanych z samopoczuciem. Pobudka (poranek), egzamin, praca przy komputerze, rozmowy (kontakty z innymi), sprzątanie (obowiązki domowe), nauka, podejmowanie decyzji, kariera, związki, samopoczucie.
Zakładamy, że dysponujemy dużą ilością zdjęć. Jeden test powinien obejmować wszystkie kategorie i zwracać tabele z procentowymi wynikami dla poszczególnych kategorii. Powiedzmy ze będzie to np. tablica integer-ów z wartościami od 0-100. Wynik powiedzmy 70 dla kategorii "praca przy komputerze" oznacza, że użytkownik w 70% wybrał zdjęcie pozytywne, co może sugerować, że tego dnia był efektywny w pracy.

Przebieg testu wygląda w ten sposób, że dwa wyżej wymienione rodzaje testów są przeplatane. To znaczy, większość czasu zajmuje gra Balloons, a od czasu do czasu (albo po przejściu do kolejnego levelu - to jest jeszcze do sprecyzowania) pokazywana jest para obrazów do wyboru. Test samopoczucia jest więc niejako przy okazji, tak aby użytkownik zbyt mocno się nad nim nie zastanawiał, co miejmy nadzieję sprowokuje bardziej spontaniczne i wiarygodne wybory.

W celu sprawniejszego podziału pracy, gra Balloons i test samopoczucia będą rozwijane osobno i zostaną zintegrowane później (albo i nie, jeżeli się okaże że tak będzie lepiej).


## Opis ogólny ##

Zadaniem zespołu D jest stworzenie części aplikacji odpowiedzialnej za dostarczanie informacji o aktualnym stanie użytkownika. Informacja ta jest potrzebna, aby można znaleźć reguły mówiące o tym, w jaki sposób aktywność użytkownika wpływa na jego stan (czym zajmują się już inne zespoły). Przez stan użytkownika rozumiemy jego sprawność intelektualną, fizyczną oraz samopoczucie. Aspekty te opiszemy szczegółowo poniżej.


## Sprawność intelektualna ##

Sprawność intelektualna jest terminem ogólnym obejmującym wiele rodzajów aktywności umysłowej, takich jak inteligencja, pamięć, koncentracja czy szybkość reakcji. Dlatego sądzimy, że właściwym podejściem do problemu jej mierzenia będzie utworzenie kilku rodzajów testów, z których każdy będzie dostosowany do konkretnego aspektu kompetencji umysłowej.

Propozycje testów do mierzenia sprawności intelektualnej:

  * Zapamiętywanie liczb
> > Zadaniem użytkownika jest zapamiętanie ciągu losowo wybranych liczb i powtórzenie ich na zawołanie. Po udzieleniu prawidłowej odpowiedzi, ilość cyfr do zapamiętania zwiększy się, w przeciwnym przypadku zmniejszy. Przeciętny człowiek jest w stanie zapamiętać około 6 lub 7 cyfr.
> > <p>Celem tego ćwiczenia jest zmierzenie tzw. werbalnej pamięci operacyjnej, której często używamy na co dzień do analizowania zdań lub zapamiętywania numerów telefonów. Ten rodzaj pamięci ma związek z inteligencją, dlatego podobne testy są często stosowane w testach na inteligencję.<br>
</li></ul><ul><li>Zapamiętywanie położenia obiektów<br>
<blockquote>W tym teście obiekty ukrywane są za kilkunastoma oknami, które co jakiś czas otwierają się, pokazując obiekt jaki jest w nich umieszczony. Zadaniem użytkownika jest zidentyfikowanie obiektu znajdującego się za zamkniętym oknem, kiedy zostanie o to poproszony.<br>
<p>Ten rodzaj testu mierzy tzw. pamięć epizodyczną. Jest to rodzaj pamięci odgrywający bardzo ważną rolę w codziennym życiu; pamięci epizodycznej używamy np. do zapamiętania, w którym miejscu zaparkowaliśmy samochód.<br>
</blockquote></li><li>Szybka ocena zdań na temat pokazanego obiektu<br>
<blockquote>Ten test pokazuje użytkownikowi rysunek (zawierający np. koło i kwadrat) oraz zdanie go opisujące (np. "Koło nie jest większe od kwadratu"). Użytkownik ma za zadanie określić czy zdanie jest prawdziwe czy fałszywe. Przy ocenie wyniku brana jest pod uwagę szybkość z jaką udzielane są odpowiedzi.<br>
<p>Ten test jest oparty na teście rozumowania gramatycznego, które jest związane z ogólną inteligencją. Mierzy on ogólną zdolność rozumowania. Zadania tego rodzaju angażują płaty czołowe mózgu.<br>
</blockquote></li><li>Test na szybkość reakcji<br>
<blockquote>Ten test może być prostym testem sprawdzającym szybkość reakcji na bodziec, np. wciśnięcie klawisza w reakcji na zmianę koloru obiektu na ekranie, bądź też prostą grą w stylu Tetris, Space Invaders itp. Ta druga opcja może być warta rozważenia ze względu na fakt, że tego rodzaju gra/ćwiczenie byłaby dla użytkownika bardziej interesująca i angażująca.</blockquote></li></ul>

Proponowane przez nas rodzaje testów są wzorowane na testach używanych do oceniania wyników programu "Brain Test Britain" (<a href='https://www.bbc.co.uk/labuk/results/braintestbritain/3_benchmark_tests.html'>https://www.bbc.co.uk/labuk/results/braintestbritain/3_benchmark_tests.html</a>). "Brain Test Britain" to interesujący eksperyment, który wykazał, że wbrew często powtarzanym opiniom, ćwiczenia umysłowe nie mają wpływu na<br>
zwiększenie wydajności umysłowej. Testy z tego eksperymentu dobrze nadają się do naszych celów, ponieważ wiele testów na kompetencję umysłową znanych w psychologii dostosowanych jest do diagnozowania sytuacji patologicznych, nie do mierzenia kompetencji osób zdrowych.<br>
<br>
W "Brain Test Britain" wykazano, że ćwiczenia nie polepszają ogólnej sprawności intelektualnej, ale tylko zwiększają kompetencję w konkretnym ćwiczeniu (potwierdzając jedynie przysłowie "praktyka czyni mistrza"). Fakt ten może sprawić problemy podczas oceny wyników, ponieważ późniejsze wyniki użytkownika mogą być lepsze po prostu ze względu na to, że osiągnął on większą wprawę, przez co ich porównywanie jest trudniejsze. Możliwymi rozwiązaniami tego problemu mogą być: oszczędne dawkowanie ćwiczeń i krótki czas ich trwania, oraz normalizacja wyników, aby zrekompensować efekt osiągania wprawy w konkretnym ćwiczeniu.<br>
<br>
<br>
<h2>Samopoczucie - odczuwany stan psychiczny i fizyczny</h2>

Termin "zdrowie psychiczne" odnosi się do dobrego samopoczucia psychicznego i emocjonalnego. Merriam-Webster definiuje zdrowie psychiczne jako stan dobrego samopoczucia psychicznego i emocjonalnego; człowiek jest w stanie używać jego zdolności poznawczych i emocji, funkcjonować w społeczeństwie oraz sprostać wymogom życia codziennego.<br>
<br>
Proponujemy przedstawić użytkownikowi, krótki test, zawierający kilka<br>
pytań i suwak, na którym będzie mógł zaznaczyć stopień prawdziwości<br>
danego stwierdzenia. Dzięki suwakowi użytkownik nie będzie musiał<br>
podejmować kolejnych decyzji (chodzi tu o testy wartościujące: zgadzam<br>
się/trochę się zgadzam/zupełnie się nie zgadzam itp.), tylko będzie<br>
zaznaczał "intuicyjnie". Poniższa lista pytań jest dość długa i<br>
oczywiście wymaga dopracowania. Ideałem było by ułożenie maksymalnie<br>
kilku (3, 4) najważniejszych pytań, do czego będziemy zmierzać.<br>
<br>
<ul><li>Czy Twoje samopoczucie było dziś dobre?<br>
</li></ul><ol><li>Spałem dobrze.<br>
</li><li>Po obudzeniu czułem, że mam dużo energii.<br>
</li><li>Czułem entuzjazm (ciekawość, pozytywne lub neutralne emocje) na myśl o wykonywaniu dziś swoich obowiązków.<br>
</li><li>Dużo się dziś śmiałem.<br>
</li><li>W ciągu dnia nie miałem problemów w podejmowaniu decyzji.<br>
</li><li>Męczyłem się dziś bardziej niż zwykle.<br>
</li><li>W ciągu dnia czułem się zniechęcony, przygnębiony i smutny.<br>
</li><li>Byłem dziś niespokojny, nie mogłem sobie znaleźć miejsca.<br>
</li><li>Złościłem się bez powodu.<br>
</li><li>Czułem się potrzebny i przydatny.<br>
</li><li>Myślę o jutrze i czekających mnie zadaniach z optymizmem (ew. entuzjazmem, ciekawością).</li></ol>

Kolejny test polega na wyborze przez użytkownika z palety kolorów,<br>
koloru, który w danym dniu najlepiej odpowiada jego nastrojowi.<br>
<br>
Proponujemy aby test kolorów przeprowadzony był rano, a test nr. 1<br>
wieczorem, dla bardziej obiektywnego wyniku.<br>
<br>
W realizacji zadania chcemy równolegle pracować nad zaprogramowaniem<br>
testów oraz dopracowaniem pytań i interpretacji.<br>
<br>
<br>
<br>
<h2>Sprawność fizyczna</h2>

Comment by daboo.g...@gmail.com, Jan 16, 2011<br>
<br>
Ćwiczenie I: Przysiady Ograniczenie czasowe: 30 sek Wynik: użytkownik wprowadza ilość zrobionych przysiadów w wyznaczonym czasie. Zalety: test wytrzymałości nóg, pleców, gibkości. Minusy: nie sprawdza sprawności górnych kończyn.<br>
<br>
W przypadku braku możliwości wykonywania przysiadów, będzie do wyboru dwa ćwiczenia zastępcze.<br>
<br>
Ćwiczenie II: Brzuszki Ograniczenie czasowe: 30 sek Wynik: użytkownik wprowadza ilość zrobionych przysiadów w wyznaczonym czasie. Zalety: test wytrzymałości pleców, brzucha, gibkości. Minusy: niedobre ćwiczenie dla osób z chorobą pleców<br>
<br>
Ćwiczenie III: Pompki przy ścianie stojąc. Osoba ćwicząca staje w odległości około 50 cm od ściany i wykonuje ruch ramion jak przy klasycznych pompkach. W razie bolów, zmiejszyć odległość od ściany. Ograniczenie czasowe: 30 sek Wynik: użytkownik wprowadza ilość zrobionych pompek w wyznaczonym czasie. Zalety: test wytrzymałości ramion, przy minimalnym obciążeniu pleców.<br>
<br>
Wszystkie ćwiczenia są ze sobą w miarę porównywalne więc równie dobrze można by każdego dnia wykonywać inne ćwiczenie.<br>
<br>
<h2>Jak to działa</h2>
<img src='http://goodfeeling.googlecode.com/svn/wiki/ZespolD/device1.png' />
<img src='http://goodfeeling.googlecode.com/svn/wiki/ZespolD/device2.png' />
<img src='http://goodfeeling.googlecode.com/svn/wiki/ZespolD/device3.png' />
<img src='http://goodfeeling.googlecode.com/svn/wiki/ZespolD/device4.png' />