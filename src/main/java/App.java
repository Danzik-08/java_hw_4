import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class App {
    public static void main(String[] args) throws IOException {
        App a = new App();
        a.test();
    }

    public void test() throws IOException {

        List<Iris> irisList = Files
                .lines(Paths.get("/Users/danzan/danzan_streamAPI/src/main/java/iris.data"))
                .map(Iris::parse)
                .collect(Collectors.toList()); //load data from file iris.data

        IrisDataSetHelper helper = new IrisDataSetHelper(irisList);

        //get average sepal width
        Double avgSepalLength = helper.getAverage(Iris::getSepalWidth);
        System.out.println(avgSepalLength);

        //get average petal square - petal width multiplied on petal length
        Double avgPetalLength = helper.getAverage(item -> item.getPetalWidth() * item.getPetalLength());
        System.out.println(avgPetalLength);

        //get average petal square for flowers with sepal width > 4
        Double avgPetalSquare = helper.getAverageWithFilter(item -> item.getSepalWidth() > 4,
                                                            item -> item.getPetalWidth() * item.getPetalLength());
        System.out.println(avgPetalSquare);

        //get flowers grouped by Petal size (Petal.SMALL, etc.)
        Map groupsByPetalSize = helper.groupBy(item -> Iris.classifyByPatel((Iris) item));
        System.out.println(groupsByPetalSize);

        //get max sepal width for flowers grouped by species
        Map maxSepalWidthForGroupsBySpecies = (Map) helper.maxFromGroupedBy(x -> Iris.getSpecies(), Iris::getSepalWidth);
        System.out.println(maxSepalWidthForGroupsBySpecies);
    }

}