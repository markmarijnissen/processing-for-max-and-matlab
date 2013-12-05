%add Processing JARS to jave classpath.
cd lib
path = [fullfile(pwd,'core.jar')]; %'\r\n' fullfile(pwd,'max.jar')] ;
path = regexprep(path,'\\','\\\\');
path = regexprep(path,'\\\\r\\\\n','\\r\\n');
cd ..

file = fopen(fullfile(matlabroot,'toolbox','local','classpath.txt'),'a+');
try
    fprintf(file,['\n' path '\n']);
catch err
    if strcmp(err.identifier,'MATLAB:FileIO:InvalidFid')
        error('MATLAB:Install:NoAdministrator','You need to be administrator to do this. Run Matlab as Administrator.');
    end
end
fclose(file);

edit(fullfile(matlabroot,'toolbox','local','classpath.txt'))
input('Press any key to exit MatLab (restart is needed)');
quit