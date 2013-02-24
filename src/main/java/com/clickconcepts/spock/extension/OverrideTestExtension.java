package com.clickconcepts.spock.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spockframework.runtime.AbstractRunListener;
import org.spockframework.runtime.extension.IGlobalExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;
import org.spockframework.util.NotThreadSafe;

import java.util.List;

@NotThreadSafe
public class OverrideTestExtension implements IGlobalExtension {

    private static final Logger LOG = LoggerFactory.getLogger(OverrideTestExtension.class);
    //    private final OverrideTestInterceptor interceptor = new OverrideTestInterceptor();

    /**
     * This method runs before the spec is run, it visits the specs before
     *
     * @param spec
     */
    @Override
    public void visitSpec(SpecInfo spec) {

        spec.addListener(new AbstractRunListener() {

            @Override
            public void beforeSpec(SpecInfo spec) {
                if (!spec.getReflection().isAnnotationPresent(OverrideFeatures.class)) {
                    LOG.debug("File {} is not annotated for overriding methods", spec.getName());
                    return;
                }

                LOG.debug("Name:{}, File:{}", spec.getName(), spec.getFilename());

                List<FeatureInfo> childFeatures = spec.getFeatures();
                List<FeatureInfo> parentFeatures = spec.getSuperSpec().getFeatures();

                for (FeatureInfo parentFeature : parentFeatures) {
                    if (isOverriddenInChild(parentFeature, childFeatures)) {
                        LOG.debug("Skipping Feature [{}] in File: {}", parentFeature.getName(), parentFeature.getParent().getFilename());
                        parentFeature.setExcluded(true);
                        parentFeature.setSkipped(true);
                    }
                }

                super.beforeSpec(spec);
            }
        });
    }

    private boolean isOverriddenInChild(FeatureInfo parentFeature, List<FeatureInfo> childFeatures) {
        boolean found = false;
        for (FeatureInfo childFeature : childFeatures) {
            if (childFeature.getName().equals(parentFeature.getName())) {
                found = true;
                break;
            }
        }
        return found;
    }
}
