(ns user
  (:require [integrant.repl :refer [go halt reset reset-all]]
            [system :as system]))

(integrant.repl/set-prep! (fn [] system/config))

(comment
  (go)
  (halt)
  (reset)
  (reset-all))

