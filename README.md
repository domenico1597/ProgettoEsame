# ProgettoEsame

# Traccia dell'esame
Obiettivo del progetto: Sviluppare un'applicazione Java che data una città italiana, analizzi le previsioni di temperatura per i prossimi 5 giorni e le confronti con la temperatura effettiva dopo 5 giorni. Calcolare statistiche sull'errore per fasce orarie (media, varianza, massimo, minimo). Filtrare per temperatura minima e massima e per fasce orarie.

# Descrizione
Il nostro applicativo è un RESTful Web Service, cioè un sistema software che, comunicando tramite il protocollo HTTP, è in grado di mettersi al servizio di un Client, come ad esempio un'applicazione, un sito web o Postman, e consentire agli utenti che vi si collegano di usufruire delle funzioni che mette a disposizione.
Il progetto implementa un servizio meteo che a seconda della rotta scelta esegue:

# Rotte
| Rotta        | Funzione                                                                     |
| ------------:| ----------------------------------------------------------------------------:|
| /forecast    |Visualizza le previsioni di 5 giorni ad intervalli di 3 ore                   |
|  /current    |Visualizza le condizioni meteorologiche attuali                               |
|  /errors     |Salva le statistiche sull'errore tra il forecast e l'attuale                  |
| /errStat     |Metodo che restituisce le statistiche sull'errore tra il forecast e l'attuale |
| /filters     |Permette di visualizzare le previsioni da un preciso momento start a finish   |

# Postman
Esempi di rotte:

**/forecast?name=ancona**


![Screenshot (182)](https://user-images.githubusercontent.com/114992312/201186559-1a049ece-c8c0-42f8-b796-6cd8e867c91f.png)











**/current?name=ancona**


![Screenshot (183)](https://user-images.githubusercontent.com/114992312/201186738-ec685e47-a93c-4f28-8954-0ac3af2e8ff6.png)




**/errors?name=ancona**


![Screenshot (184)](https://user-images.githubusercontent.com/114992312/201186755-983e177d-5cfb-43a7-a730-0e939642cf5a.png)



**/errStat?name=ancona**


![Screenshot (185)](https://user-images.githubusercontent.com/114992312/201186772-2bb678fc-b3e1-41af-8adc-20de565450d7.png)


**/filters?start=10-11-2022&finish=12-11-2022&startTime=9:30&finishTime=11:30**


![Screenshot (186)](https://user-images.githubusercontent.com/114992312/201187060-79b21144-07d5-4e6b-9be5-947c4bc9b9da.png)

