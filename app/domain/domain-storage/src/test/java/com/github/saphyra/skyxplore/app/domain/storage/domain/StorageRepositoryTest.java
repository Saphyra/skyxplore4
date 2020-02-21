package com.github.saphyra.skyxplore.app.domain.storage.domain;

import com.github.saphyra.skyxplore.app.common.test.RepositoryTestConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class StorageRepositoryTest {
    private static final String USER_ID_1 = "user-id-1";
    private static final String USER_ID_2 = "user-id-2";

    @Autowired
    private StorageRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByUserId() {
        //GIVEN
        Storage storage1 = createStorage(USER_ID_1);
        Storage storage2 = createStorage(USER_ID_2);

        underTest.save(storage1);
        underTest.save(storage2);
        //WHEN
        underTest.deleteByUserId(USER_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsExactly(storage2);
    }

    private Storage createStorage(String userId) {
        return Storage.builder()
            .storageKey(
                StorageKeyId.builder()
                    .storageKey(StorageKey.LOCALE)
                    .userId(userId)
                    .build()
            ).build();
    }
}