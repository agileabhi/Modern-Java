import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    //Defining conditions as predicates
    public static final Predicate<String> LENGTH_FIVE = s -> s.length() == 5;
    public static final Predicate<String> STARTS_WITH_S =
            s -> s.startsWith("S");
    public static void main(String[] args) {



        //Company.getName();Employee.getName();


        System.out.println("Hello world!");
        //anonymous inner class; company is an interface but can be used like below
        Company comp = new Company() {
            @Override
            public int getNumberOfEmployees() {
                return 100;
            }
        };
        //using lambda
        Company comp1 = () -> 0;
        System.out.println(comp1.hashCode());
        Consumer<String> consumer = (s) -> System.out.println();


        Thread t1 = new Thread(() -> System.out.println("new thread"));
        //Thread t2 = new Thread(System.out::print());
        List<String> strings =
                Arrays.asList("this", "is", "a", "list", "of", "strings");
        List<String> sorted = strings.stream()
                .sorted((s1, s2) -> s1.compareTo(s2))
                .collect(Collectors.toList());
        //  sorted = strings.stream()
        //        .sorted(String::compareTo)
        //      .collect(Collectors.toList());

        String city = "Munich";
        Supplier<String> lambda = city::toUpperCase;
        Supplier<String> lambda1 = () -> city.toUpperCase();
        System.out.println(lambda.get());
        //java.util.function package
        //consumer;forEach method from Iterable takes consumer as an argument;
        List<String> stringList = Arrays.asList("this", "is", "a", "list", "of", "strings");
        stringList.add("x");
        stringList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        stringList.forEach(x -> System.out.println(x));
        stringList.forEach(System.out::println);
        Consumer<String> c = s-> System.out.println(s);
        stringList.forEach(c);
        //supplier
        DoubleSupplier randomSupplier = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return Math.random();
            }
        };
        randomSupplier = () -> Math.random();
        randomSupplier = Math::random;
        //differed execution
        //predicates are used to filter elements in a stream

        // Function interface => R apply(T t) => most common usage is Stream.map

        List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
                "Zoë", "Jayne", "Simon", "River", "Shepherd Book");
        List<Integer> nameLengths = names.stream()
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        return s.length();
                    }
                })
                .collect(Collectors.toList());
        nameLengths = names.stream()
                .map(s -> s.length())
                .collect(Collectors.toList());
        nameLengths = names.stream()
                .map(String::length)
                .collect(Collectors.toList());

        //Map.computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) k->
        //Comparator.comparing(Function<? super T,? extends U> keyExtractor)

//        Streams are also lazy. A stream will only process as much data as is necessary to reach
//        the terminal condition

//        Streams are already lazy and do not process elements until a terminal condition is
//        reached. Then each element is processed individually. If there is a short-circuiting
//        operation at the end, the stream processing will terminate whenever all the conditions
//        are satisfied.
       /* This is one of the great advantages of stream processing over working with collections
        directly. With a collection, all of the operations would have to be performed
        before moving to the next step. With streams, the intermediate operations form a
        pipeline, but nothing happens until the terminal operation is reached. Then the
        stream processes only as many values as are necessary. */
        OptionalInt firstEvenDoubleDivBy3 = IntStream.range(100, 200)
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .findFirst();
        System.out.println(firstEvenDoubleDivBy3);


// ...


      Main m = new Main();
      m.firstDivBy3();
    }

    public int firstDivBy3(){
        OptionalInt firstEvenDoubleDivBy3 = IntStream.range(100, 200)
                .map(this::multByTwo)
                .filter(this::divByThree)
                .findFirst();
        OptionalInt oi = IntStream.range(100, 200)
                .map(e->e*2)
                .filter(e->e%3==0)
                .findFirst();
        return firstEvenDoubleDivBy3.getAsInt();
    }

    public int multByTwo(int n) {
        System.out.printf("Inside multByTwo with arg %d%n", n);
        return n * 2;
    }
    public boolean divByThree(int n) {
        System.out.printf("Inside divByThree with arg %d%n", n);
        return n % 3 == 0;
    }
    public String getNamesOfLength(int length, String... names) {
        return Arrays.stream(names)
                .filter(s -> s.length() == length)
                .collect(Collectors.joining(", "));//mutable reduction operation
    }

    public String getNamesStartingWith(String s, String... names) {
        return Arrays.stream(names)
                .filter(S -> S.startsWith(s))
                .collect(Collectors.joining(", "));
    }

    public String getNamesSatisfyingCondition(
            Predicate<String> condition, String... names) {
        return Arrays.stream(names)
                .filter(condition)
                .collect(Collectors.joining(", "));
    }

    //creating streams
    public void createStream(){
        //Use the static factory methods in the Stream interface, or the stream methods on
        //Iterable or Arrays.
        String names = Stream.of("Gomez", "Morticia", "Wednesday", "Pugsley")
                .collect(Collectors.joining(","));
        System.out.println(names);
        //Since you have to create an array ahead of time, this approach is less convenient, but
        //works well for variable argument lists.
        //using Arrays.stream directly
        String[] munsters = { "Herman", "Lily", "Eddie", "Marilyn", "Grandpa" };
        names = Arrays.stream(munsters)
                .collect(Collectors.joining(","));
        System.out.println(names);
        //Another static factory method in the Stream interface is iterate.
        //returns an infinite (emphasis added) sequential
        //ordered Stream produced by iterative application of a function f to an initial element
        //seed.
        //What if the argument and return type are the same? The java.util.function package
        //defines UnaryOperator for that

        List<BigDecimal> nums =
                Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE) )
                        .limit(10)
                        .collect(Collectors.toList());
        System.out.println(nums);
// prints [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        Stream.iterate(LocalDate.now(), ld -> ld.plusDays(1L))
                .limit(10)
                .forEach(System.out::println);

Stream.generate(Math::random)
.limit(10)
.forEach(System.out::println);
//If you already have a collection, you can take advantage of the default method
//stream that has been added to the Collection interface,

        List<String> bradyBunch = Arrays.asList("Greg", "Marcia", "Peter", "Jan",
                "Bobby", "Cindy");
        names = bradyBunch.stream()
                .collect(Collectors.joining(","));

        List<Integer> ints = IntStream.range(10, 15)
                .boxed()
                .collect(Collectors.toList());
        //Necessary for Collectors to convert primitives to List<T>

//When dealing with streams of objects, you can convert from a stream to a collection
//using one of the static methods in the Collectors class. For example, given a stream
//of strings, you can create a List<String> using the code in Example 3-8.

        List<String> strings = Stream.of("this", "is", "a", "list", "of", "strings")
                .collect(Collectors.toList());

    }







}

