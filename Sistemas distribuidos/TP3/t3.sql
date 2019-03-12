CREATE OR REPLACE FUNCTION t3() RETURNS TRIGGER AS $T3$
    DECLARE
    BEGIN
        UPDATE depto SET nemp = 0 WHERE deptnum = NEW.deptnum;
        IF (OLD.deptnum != NEW.deptnum) THEN
            UPDATE employee SET deptnum = NEW.deptnum WHERE deptnum = OLD.deptnum;
        END IF;
        
    RETURN NULL;
END;
    
$T3$ LANGUAGE plpgsql;


CREATE TRIGGER trigger3 
AFTER UPDATE of deptnum
ON depto
FOR EACH ROW
EXECUTE PROCEDURE t3(); 

            
            
            
        
