package com.github.saphyra.skyxplore.app.game_data.domain.building.production;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.app.domain.common.SkillType;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.game_data.domain.ConstructionRequirementsValidator;

@RunWith(MockitoJUnitRunner.class)
public class ProductionValidatorTest {
    private static final String ID = "id";

    @Mock
    private ConstructionRequirementsValidator constructionRequirementsValidator;

    @InjectMocks
    private ProductionValidator underTest;

    private Map<String, Production> item = new HashMap<>();

    private Production production = new Production();

    @Mock
    private ConstructionRequirements constructionRequirements;

    @Before
    public void setUp() {
        item.put(ID, production);

        production.setPlaced(Arrays.asList(SurfaceType.COAL_MINE));
        production.setAmount(1);
        production.setRequiredSkill(SkillType.AIMING);
        production.setConstructionRequirements(constructionRequirements);
    }

    @Test(expected = NullPointerException.class)
    public void nullItem() {
        underTest.validate(null);
    }

    @Test(expected = IllegalStateException.class)
    public void emptyItem() {
        underTest.validate(new HashMap<>());
    }

    @Test(expected = IllegalStateException.class)
    public void nullProduction() {
        item.put(ID, null);

        underTest.validate(item);
    }

    @Test(expected = IllegalStateException.class)
    public void nullPlaced() {
        production.setPlaced(null);

        underTest.validate(item);
    }

    @Test(expected = IllegalStateException.class)
    public void placedContainsNull() {
        List<SurfaceType> surfaceTypes = new ArrayList<>();
        surfaceTypes.add(null);
        production.setPlaced(surfaceTypes);

        underTest.validate(item);
    }

    @Test(expected = IllegalStateException.class)
    public void nullAmount() {
        production.setAmount(null);

        underTest.validate(item);
    }


    @Test(expected = IllegalStateException.class)
    public void zeroAmount() {
        production.setAmount(0);

        underTest.validate(item);
    }

    @Test(expected = IllegalStateException.class)
    public void nullRequiredSkill() {
        production.setRequiredSkill(null);

        underTest.validate(item);
    }

    @Test(expected = IllegalStateException.class)
    public void nullConstructionRequirements() {
        production.setConstructionRequirements(null);

        underTest.validate(item);
    }

    @Test
    public void valid() {
        underTest.validate(item);

        verify(constructionRequirementsValidator).validate(constructionRequirements);
    }
}