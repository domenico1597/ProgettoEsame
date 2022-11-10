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
