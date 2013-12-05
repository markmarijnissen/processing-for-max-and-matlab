clear java      % clear java 
clear classes   % clear dynamic classpath; load classes again

%should point to (parent directory) of .class files
javaclasspath('classes'); 

import nl.madebymark.Processing.* 

% Run sketch instantly
Processing.run('nl.madebymark.Processing.HelloWorld');

% Run sketch by instantiatign a Processing class
p = Processing('nl.madebymark.Processing.HelloWorld',true);
p.show;

% you can click the window away, but the sketch will keep running
% because persistent = true in the constructor!
%so you need to manually dispose the sketch!
p.dispose; 

% couple of handy methods to explore java-classes in Matlab:
methodsview Processing
methods Processing
% methods Processing -full