## Konwencja językowa ##
Nazwy wszystkich zmiennych, javadoc jak i zwykłe komentarze piszemy wyłącznie po angielsku. Sama aplikacja (z perspektywy użytkownika) również jest po angielsku.


## Konwencje dotyczące pakietów ##

> ~/src/goodfeeling/userstate – tutaj swoje prace zamieszcza grupa D, zajmująca się testami

> ~/src/goodfeeling/db – wyniki prac zespołu zajmującego się bazami danych

> ~/src/goodfeeling/gui – grupa pracująca nad GUI

> ~/src/goodfeeling/weka - warstwa integracji z algorytmami z programu Weka

> ~/src/goodfeeling/common - klasy używane przez wiele zespołów, nie pasujące do wyżej wymienionych pakietów

> ~/src/goodfeeling/misc - folder na pliki "różne"


## Nazewnictwo zmiennych w **strings.xml** ##
Jest mała szansa iż wejdziemy sobie w przestrzenie nazw, ale proponuję by w strings.xml gdzie przechowywane są łańcuchy znaków nazwy poprzedzać przedrostkiem oznaczającym pakiet, w którym go wykorzystujemy, np:



&lt;string name=\*"start\_info"\*&gt;

 nazwać od razu (w przypadku picture.test)



&lt;string name=\*"picture\_test\_start\_info"\*&gt;




## Nazewnictwo ogólne w **main.xml** ##
main.xml należy tylko do grupy GUI - chociaż i im proponuje stworzenie głównego pliku interfejsu jako np. gui.xml.

_stare: Proponuję by id obiektów nie nazywać np. "button1" ani innymi nazwami mało wymownymi - chodzi o to by czasami się nie okazało, że po integracji  różnych części nie okazało się, że jest 5x"button1" (:_



_W razie czego proszę dopisywać kolejne konwencje i ewentualne uwagi._