package org.acme;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Path;


@ApplicationScoped
public class InferenceConfiguration {

    @ConfigProperty(name = "fraud.threshold", defaultValue = "0.95")
    private float threshold;

    @ConfigProperty(name = "fraud.model.path")
    private Optional<Path> modelPath;

    ZooModel<TransactionDetails, Boolean> zooModel;

    @Startup
    void initializeModel() throws ModelNotFoundException, MalformedModelException, IOException {

        final Criteria.Builder<TransactionDetails, Boolean> builder = Criteria.builder()
            .setTypes(TransactionDetails.class, Boolean.class);

        if (modelPath.isEmpty()) {
            String modelLoction = Thread.currentThread()
                .getContextClassLoader()
                .getResource("model.onnx").toExternalForm();
            builder.optModelUrls(modelLoction);
        } else {
            builder.optModelPath(modelPath.get());
        }

        final Criteria<TransactionDetails, Boolean> criteria =
            builder.optTranslator(new TransactionTransformer(threshold))
                .optEngine("OnnxRuntime")
                .optProgress(new ProgressBar())
                .build();

        this.zooModel = criteria.loadModel();
    }

    @Produces
    ZooModel<TransactionDetails, Boolean> zooModel() {
        return this.zooModel;
    }

    @Produces
    @RequestScoped
    Predictor<TransactionDetails, Boolean> predictor(ZooModel<TransactionDetails, Boolean> zooModel) {
        System.out.println("New Predictor");
        return zooModel.newPredictor();
    }

    void close(@Disposes Predictor<TransactionDetails, Boolean> predictor) {
        System.out.println("Closing Predictor");
        predictor.close();
    }
}
