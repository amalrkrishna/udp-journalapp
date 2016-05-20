# udp-journalapp

Java UDP Journaling Application

The journaling application is designed so that a client can access their journal entries from anywhere. They can store any text information in their journal. This journaling system is set up to support the following operations:

1. The client’s user name is used by the server to locate journal entries made by that client.
2. When the client starts the journal application, he or she must identify themselves with their user name and indicate which journal entry they want to work on. Journal entries are identified by the month, day, and year. There is only one journal entry per month-day-year per client.
3. A client can access the journal entry from any date. For example, a client may create a new journal entry in the morning. Later in the day, the client may continue working on the journal entry for that day. This means, the server retrieves the journal entry and sends it to the client where is it displayed in a text area. The client can change the entry or add to it. When finished, the client can once again save the entry, which sends the updated text to the server for storage.
4. From the above operations, the journal server must support reading a journal entry for a specific client user name and month-day-year. It must also support saving a journal entry for a specific client user name and month-day-year.

UDP is used for communications between the client and the server. All client journal entries is saved to files.
