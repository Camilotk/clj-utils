(ns camilotk.commands
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:gen-class))

(def root (atom (if (= (System/getProperty "os.name") "Windows")
                  (or (System/getenv "USERPROFILE")
                      (str (System/getenv "HOMEDRIVE")
                           (System/getenv "HOMEPATH")))
                  (System/getenv "HOME"))))

(defn change-root! [path]
  (reset! root path))

(defn current-path []
  (println (.getAbsolutePath (io/file @root))))

(defn list-files [] 
  (let [file (io/file @root)
        contents (.list file)] 
    (doseq [content contents]
      (print (str content "\t")))
    (flush)
    (println)))

(defn change-dir [& args]
  (let [location (second (first args))
        current-path      @root
        new-path (if (clojure.string/ends-with? current-path "/")
                   (str current-path location)
                   (str current-path "/" location))]
    (if (= location "..")
      (change-root! (.getParent (io/file current-path)))
      (if (.exists (io/file new-path))
        (change-root! new-path)
        (println (str "folder not exists: " location))))))

(defn finish-commands-repl []
  (System/exit 0))