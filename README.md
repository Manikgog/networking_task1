HTTP сервер своими руками
1. Написать HTTP Server
2. Написать HTTP Client
3. Клиент отправляет json файл:
   {
   "info": "salary.by",
   "employees": [
   {
   "id": "01",
   "name": "Иванов И.И.",
   "salary": 500,
   "tax": 200
   },
   {
   "id": "02",
   "name": "Петров П.П.",
   "salary": 1500,
   "tax": 100
   },
   {
   "id": "03",
   "name": "Сидоров С.С.",
   "salary": 5500,
   "tax": 50
   },
   {
   "id": "04",
   "name": "Смирнов С.С.",
   "salary": 4500,
   "tax": 50
   }
   ]
   }


4. Сервер парсит json и возвращает html файл с суммой зарплат:

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Salary</title>
</head>
<body>

<table>
    <tr>
        <th>Total income</th>
        <th>Total tax</th>
        <th>Total profit</th>
    </tr>
    <tr>
        <td>${total_income}</td>
        <td>${total_tax}</td>
        <td>${total_profit}</td>
    </tr>
</table>
</body>
</html>

Где
total Income - суммарный доход total_tax- суммарный налог total_profit - итоговая чистая прибыль
5. Клиент сохраняет файл на диске в формате html
* Дополнительное задание по желанию: сделать так, чтобы сервер читал весь файл, присланный клиентом. Для этого надо парсить заголовок content-length
