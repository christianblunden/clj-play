# Minimal integrant reload example

This is a minimal example of workflow reloaded using integrant.  It shows how to both launch the system as a production system and for use via the REPl.

The system comprises of 3 dependencies web server, handlers and database.

Test the server by `curl http://localhost:3000/api/do-stuff`

## Starting system

**command line**

```bash
clojure -Aserver
```

**REPL**

Start a REPL, with the dev user namespace loaded

```bash
clj -Adev
```
Then in the REPL

```clojure
;; Start the system
(go)

;; Restart the system
(reset-all)
```

There are some convenience methods commented out in the user.clj namespace which can be run if nrepl is jacked-in from IDE.