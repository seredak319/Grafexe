# Grafexe

Grafexe jest to program stworzony w ramach szkolnego projektu, który związany był z algorytmami operującymi na grafach.
Program napisany został w języku Java, całkowicie samodzielnie.

### Co potrafi program?

Program potrafi:
* Wygenerować graf o zadanej liczbie kolumn i wierszy węzłów i wagach krawędzi losowanych w zadanym zakresie wartości oraz z procentowym zagęszczeniem połączeń.
* Zapisać taki graf do pliku o ustalonym formacie.
* Przeczytać graf z pliku o ustalonym formacie.
* Sprawdzić spójność grafu (algorytm przeszukiwanie grafu wszerz - BFS).
* Znaleźć w tym grafie najkrótsze ścieżki pomiędzy wybranymi parami węzłów, wykorzystując Algorytm Dijkstry.

Ponadto program wyposażony jest w interfejs graficzny (widoczny poniżej), który zapewnia:
* Narysowanie grafu jako siatki (algorytmy przystosowane dla wszelkiego rodzaju grafów).
* Wybór węzłów do wyznaczania ścieżki za pomocą myszki.
* Pokazanie najkrótszej ścieżkę na rysunku.

<img width="976" alt="grafexe" src="https://user-images.githubusercontent.com/95620581/186385068-26641a01-cdc0-40f7-873d-25358877ea04.png">

### Jak powstał program?
Grafexe w odróżnieniu od innych programów powstawał jako projekt. W pierwszej kolejności tworzone były specyfikacje, funkcjonalna oraz implementacyjna.
Następnie z wykorzystaniem kontroli wersji dodawane były kolejne funkcjonalności. Cały proces tworzenia programu kontrolowany był przez prowadzącego zajęcia.

### Czego nauczył mnie program?
Wykonanie tego projektu bez wątpienia nauczyło mnie wielu nowych rzeczy. Pierwszą z nich był nowy język programowania Java. Drugą ważną rzeczą było dokładne zaplanowanie programu, pisanie specyfikacji oraz następnie weryfikowanie jej w rzeczywistości. Ponadto podczas pisania algorytmów wykorzystywanych przez program miałem okazję porównać je z wcześniej napisanymi algorytmami w języku C. Z powodu dwóch różnych implementacji mogłem dostrzec jak zmienia się wykorzystanie pamięci, czy sam czas operacji przy tym samym rezultacie. (BFS, Dijkstra oraz przechowywanie grafu). Zdecydowaną nowością był dla mnie interfejs graficzny, który okazał się świetną pomocą jak i niesamowicie satysfakcjonującą zabawą. Co więcej nauczył mnie "myśleć za użytkownika" przewidywać różne scenariusze oraz odpowiednio je zabezpieczać:

<img width="976" alt="grafexebad" src="https://user-images.githubusercontent.com/95620581/186386821-6871d5fc-917b-4386-9ccc-92469eb58a87.png">

Co więcej w trakcie tworzenia programu, tworzyłem testy jednostkowe, które okazały się bardzo pomocne. Pozwalają jednym kliknięciem ominąć sporą część kodu w poszukiwaniu problemu.

### Czy projekt jest skończony? Czy coś jest do poprawy?
Program działa zgodnie z założeniami, natomiast należałoby wykonać jeszcze parę poprawek i modyfikacji.
W pierwszej kolejności poddałbym plik 'StartScreenController' refaktoryzacji, ponieważ ma on ponad 750 linii, a jest spory potencjał w rozbiciu go na mniejsze partie kodu. Ponadto dodałbym wielowątkowość oraz przycisk umożliwiający przerwanie zbyt długo trwającej operacji.