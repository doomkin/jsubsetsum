# SubsetSumSolver 

## Аннотация
**SubsetSumSolver** - параллельный статистический алгоритм для решения задачи о сумме подмножества.

## Задача
Из заданного набора натуральных чисел требуется выбрать элементы, чья сумма является заданным натуральным числом. _Это NP-полная задача._

## Решение
Предложенный алгоритм быстрее находит подмножество, если среднее значение исходного набора близко к среднему значению искомого подмножества. Алгоритм сортирует исходный набор в порядке возрастания отклонений от среднего и создает вычислительные процессы для перечисления k-комбинаций. Процессы создаются в порядке возрастания отклонений от среднего размера искомого подмножества. Комбинации перечисляются в порядке, предпочитающем подмножества с наиболее средними элементами.

Существование решения проверяется динамическим программированием перед началом перебора.

## Использование
```bash
java -jar subsetsum.jar input.txt output.txt
```
В `input.txt` в первой строке задаётся искомая сумма, в остальных строках задаётся исходный набор. Найденное подмножество, дающее указанную сумму, записывается в файл `output.txt`. Если файлы `input.txt` и `output.txt` не указаны, тогда ввод и вывод консольный.   

## Пример `input.txt`
```
1410635
398783
159780
144246
214206
357011
395957
135370
135370
395218
327352
125161
125161
125161
74902
137853
212109
350177
350177
286650
386766
386766
346624
296594
409697
375684
193725
355194
325752
233679
361604
86992
228487
370291
83441
304824
246591
147218
114924
232419
253338
263320
243617
129820
44383
383695
383695
323119
376973
376973
64715
285873
275474
184233
184233
153788
153788
230496
179899
38169
38169
151509
151509
204146
288406
198280
216303
227685
196768
254257
200765
174631
169712
159403
100490
109627
32677
28849
186929
150036
259617
259617
122117
112564
112564
156413
73578
366829
366829
366829
313195
150734
188740
368133
309207
309207
354715
354715
```

## Пример `output.txt`
```
228487
230496
227685
198280
137853
323119
64715
```