(ns camilotk.application
  (:require  [clojure.string :as str]
             [camilotk.commands :as commands])
  (:gen-class))

(defn prompt []
  (print "$> ")
  (flush)
  (read-line))

(defn parse-command [command]
  (cond (= "ls"   (str/lower-case command)) (commands/list-files)
        (= "pwd"  (str/lower-case command)) (commands/current-path )
        (= "exit" (str/lower-case command)) (commands/finish-commands-repl)
        (str/starts-with? command "cd")     (commands/change-dir (str/split command #"\s")) 
        :else (println "Command not found!")))

(defn executar []
  (while true
    (parse-command (prompt))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Welcome to the Clojure File System!")
  (executar))