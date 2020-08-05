;-----------------------------------------------------HW11--------------------------------------------------------------
(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (proto-get (obj :prototype) key)))

(defn proto-call [this key & args]
  (apply (proto-get this key) this args))

(defn field [key]
  (fn [this] (proto-get this key)))

(defn method [key]
  (fn [this & args] (apply proto-call this key args)))

(def value_ (field :value))
(def operands_ (field :operands))
(def operation_ (field :operation))
(def operationToStr_ (field :opToStr))
(def diff-ed (field :diff-ed))
(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))
(def toStringSuffix (method :toStringSuffix))

(declare zero one)

(def Const
  {:evaluate       (fn [this var] (value_ this))
   :toString       #(format "%.1f" (value_ %))
   :diff           (fn [this var] zero)
   :toStringSuffix #(toString %)
   })

(def Var
  {:evaluate       #(get %2 (clojure.string/lower-case (first (value_ %1))))
   :toString       #(str (value_ %))
   :diff           #(if (= %2 (value_ %1)) one zero)
   :toStringSuffix #(toString %)
   })

(defn Constant [value]
  (assoc Const
    :value value))

(def zero (Constant 0))
(def one (Constant 1))

(defn Variable [value]
  (assoc Var
    :value (str value)))

(def FunctionProto
  {:evaluate       (fn [this var]
                     (apply (operation_ this) (map #(evaluate % var) (operands_ this))))
   :toString       (fn [this] (str "(" (operationToStr_ this) " "
                                   (clojure.string/join
                                     " " (map #(toString %) (operands_ this))) ")"))
   :diff           (fn [this var]
                     (apply (diff-ed this) (concat (operands_ this) (map #(diff % var) (operands_ this)))))
   :toStringSuffix (fn [this] (str "("
                                   (clojure.string/join
                                     " " (map #(toStringSuffix %) (operands_ this))) " "
                                   (operationToStr_ this) ")"))
   })

(defn Function [operation opToStr diff-ed]
  (fn [& operands]
    (assoc FunctionProto
      :operation operation
      :opToStr opToStr
      :diff-ed diff-ed
      :operands operands)))


(def Add (Function + "+" #(Add %3 %4)))
(def Subtract (Function - "-" #(Subtract %3 %4)))
(def Multiply (Function * "*" #(Add (Multiply %3 %2) (Multiply %1 %4))))
(def Divide (Function #(/ (double %1) %2) "/" #(Divide (Subtract (Multiply %3 %2) (Multiply %1 %4)) (Multiply %2 %2))))
(def Negate (Function - "negate" #(Negate %2)))
(def Exp (Function #(Math/exp %) "exp" #(Multiply %2 (Exp %1))))
(def Ln (Function #(Math/log (Math/abs (double %))) "ln" #(Divide %2 %1)))

(def operations {'+      Add
                 '-      Subtract
                 '*      Multiply
                 '/      Divide
                 'negate Negate
                 'exp    Exp
                 'ln     Ln})

(def variables #{'x 'y 'z})

(defn firstVar [vars] (symbol (clojure.string/lower-case (str (first (name vars))))))

(defn variables? [symbols]
  (if (or (instance? clojure.lang.Cons symbols) (instance? clojure.lang.PersistentList symbols))
    false
    (contains? variables (firstVar symbols))
    )
  )

(defn recursive-parse [expression]
  (cond
    (number? expression) (Constant expression)
    (variables? expression) (Variable expression)
    :else (let [operation (operations (first expression))
                operands (map recursive-parse (rest expression))]
            (apply operation operands))))

(defn parseObject [expression]
  (recursive-parse (read-string expression)))
;-----------------------------------------------------HW12--------------------------------------------------------------
;=======================================================================================================================
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)
;=======================================================================================================================
(defn _empty [value] (partial -return value))
(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))
(defn _map [f result]
  (if (-valid? result)
    (-return (f (-value result)) (-tail result))))
(defn _combine [f a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar)
        (_map (partial f (-value ar))
              ((force b) (-tail ar)))))))
(defn _either [a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p]
  (fn [input]
    (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))
;=======================================================================================================================
(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (partial _map f) parser))
(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))
(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps]
  (reduce _either p ps))
(defn +opt [p]
  (+or p (_empty nil)))
(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))
;=======================================================================================================================
(def *all-chars (mapv char (range 32 128)))
(def *digit (+char "0123456789"))
;;(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))
;;(def *identifier (+str (+seqf cons *letter (+star (+or *letter *digit)))))
;;(def *string (+seqn 1 (+char "\"") (+str (+star (+char-not "\""))) (+char "\"")))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
;=======================================================================================================================
(declare *value)
(def *number (+map read-string (+str (+seqf #(concat (str %1) %2 (str %3) %4) (+opt (+char "+-")) (+plus *digit) (+char ".") (+plus *digit)))))
(def *negate (+seqf (constantly 'negate) (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e")))
(def *operation (+seqf #(symbol (str %)) (+or (+char "+-*/") *negate)))
(def *variable (+seqf symbol (+str (+plus (+char "xyzXYZ")))))
(def unparsed (+seqn 0 *ws (delay *value) *ws))
(def *seqSuffix (+seqn 1 (+char "(") (+opt (+seqf cons unparsed (+star unparsed))) (+char ")")))
(def *seqPrefix (+map #(cons (last %) (drop-last %)) *seqSuffix))
(def *value (+or *number *variable *operation *seqPrefix))

(defn parseObjectSuffix [expression]
  (recursive-parse ((+parser unparsed) expression)))
;-----------------------------------------------------------------------------------------------------------------------