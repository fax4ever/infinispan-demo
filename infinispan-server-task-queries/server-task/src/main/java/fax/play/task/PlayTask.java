package fax.play.task;

import java.util.Map;
import java.util.Optional;

import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;

public class PlayTask implements ServerTask<String> {

   private static final ThreadLocal<TaskContext> taskContext = new ThreadLocal<>();

   @Override
   public void setTaskContext(TaskContext context) {
      taskContext.set(context);
   }

   @Override
   public String call() throws Exception {
      TaskContext ctx = taskContext.get();
      Object name = ctx.getParameters().orElse(Map.of("name", "no-name")).get("name");
      return "Hello " + name;
   }

   @Override
   public String getName() {
      return "hello-task";
   }
}
