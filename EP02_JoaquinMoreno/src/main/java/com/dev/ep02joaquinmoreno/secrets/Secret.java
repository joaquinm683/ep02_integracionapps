package com.dev.ep02joaquinmoreno.secrets;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="secret")
public class Secret {

    @Id
    private ObjectId id;
    private String key;
    private String value;

}
