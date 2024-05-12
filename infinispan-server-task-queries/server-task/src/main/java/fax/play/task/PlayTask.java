package fax.play.task;

import java.util.Map;
import java.util.Optional;

import org.infinispan.Cache;
import org.infinispan.commons.api.query.Query;
import org.infinispan.commons.api.query.QueryResult;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;
import org.infinispan.tasks.TaskInstantiationMode;

import fax.play.model.Play;

public class PlayTask implements ServerTask<String> {

   private static final ThreadLocal<TaskContext> taskContext = new ThreadLocal<>();

   @Override
   public String getName() {
      return "hello-task";
   }

   @Override
   public TaskExecutionMode getExecutionMode() {
      return TaskExecutionMode.ONE_NODE;
   }

   @Override
   public TaskInstantiationMode getInstantiationMode() {
      return ServerTask.super.getInstantiationMode();
   }

   @Override
   public void setTaskContext(TaskContext context) {
      taskContext.set(context);
   }

   @Override
   public String call() throws Exception {
      TaskContext ctx = taskContext.get();
      Cache<?, ?> cache = ctx.getCache().orElseThrow();
      Query<Play> query = cache.query("from fax.play.model.Play where description : 'board' order by name");
      QueryResult<Play> result = query.execute();
      return result.count().value() + "";
   }

}
