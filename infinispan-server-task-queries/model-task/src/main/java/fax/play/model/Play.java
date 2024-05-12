package fax.play.model;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoSchema;

@Indexed
public class Play {

   private final String name;
   private final String description;
   private final Integer value;

   @ProtoFactory
   public Play(String name, String description, Integer value) {
      this.name = name;
      this.description = description;
      this.value = value;
   }

   @Keyword(projectable = true, sortable = true)
   @ProtoField(1)
   public String getName() {
      return name;
   }

   @Text
   @ProtoField(2)
   public String getDescription() {
      return description;
   }

   @Basic(projectable = true, sortable = true, aggregable = true)
   @ProtoField(3)
   public Integer getValue() {
      return value;
   }

   @ProtoSchema(includeClasses = {Play.class})
   public interface PlaySchema extends GeneratedSchema {
      PlaySchema INSTANCE = new PlaySchemaImpl();
   }
}
