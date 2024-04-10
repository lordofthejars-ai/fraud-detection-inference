package org.acme;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class InferenceConfiguration {

    @ConfigProperty(name = "fraud.threshold", defaultValue = "0.95")
    private float threshold;

    @ConfigProperty(name = "fraud.model.path", defaultValue = "src/main/resources/model.onnx")
    private String modelPath;

    @Produces
    public Criteria<TransactionDetails, Boolean> criteria() {
        Path modelPath = Paths.get(this.modelPath);
        return
                Criteria.builder()
                        .setTypes(TransactionDetails.class, Boolean.class)
                        .optTranslator(new TransactionTransformer(threshold))
                        .optModelPath(modelPath)
                        .optEngine("OnnxRuntime")
                        .optProgress(new ProgressBar())
                        .build();
    }

    @Produces
    public ZooModel<TransactionDetails, Boolean> zooModel(Criteria<TransactionDetails, Boolean> criteria) throws ModelNotFoundException, MalformedModelException, IOException {
        return criteria.loadModel();
    }

    @Produces
    public Predictor<TransactionDetails, Boolean> predictor(ZooModel<TransactionDetails, Boolean> zooModel) {
        return zooModel.newPredictor();
    }

    void close(@Disposes Predictor<TransactionDetails, Boolean> predictor) {
        predictor.close();
    }
}
