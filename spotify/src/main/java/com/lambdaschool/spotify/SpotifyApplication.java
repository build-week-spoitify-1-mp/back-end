package com.lambdaschool.spotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpotifyApplication
{
   /**
    * Connect to the system environment where environment variables live.
    */
   @Autowired
   private static Environment env;

   /**
    * If an environment variable is not found, set this to true
    */
   private static boolean stop = false;

   /**
    * If an application relies on an environment variable, check to make sure that environment variable is available!
    * If the environment variable is not available, you could set a default value, or as is done here, stop execution of the program
    *
    * @param envvar The system environment where environment variable live
    */
   private static void checkEnvironmentVariable(String envvar)
   {
      if (System.getenv(envvar) == null)
      {
         stop = true;
      }
   }
   public static void main(String[] args)
   {
      if (!stop)
      {
         SpringApplication.run(SpotifyApplication.class, args);
      }
   }

}
