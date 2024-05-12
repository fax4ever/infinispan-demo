package fax.play.task;

import java.util.List;

import org.infinispan.Cache;
import org.infinispan.commons.api.query.Query;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;
import org.infinispan.tasks.TaskInstantiationMode;

import fax.play.model.Play;

public class PlayTask implements ServerTask<Integer> {

   private static final ThreadLocal<TaskContext> taskContext = new ThreadLocal<>();

   @Override
   public String getName() {
      return "play-task";
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
   public Integer call() {
      TaskContext ctx = taskContext.get();
      Cache<Integer, Play> cache = (Cache<Integer, Play>) ctx.getCache().orElseThrow();
      cache.put(2, new Play("Chess", "Chess is an abstract strategy game that involves no hidden information on the board.", 9));
      Query<Play> query = cache.query("from fax.play.model.Play where description : 'board' order by name");
      List<Play> list = query.list();
      if (!list.isEmpty()) {
         Play play = list.getFirst();
         cache.put(3, play);
      }
      return list.size();
   }
}
