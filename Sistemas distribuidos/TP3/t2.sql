

CREATE OR REPLACE FUNCTION t2() RETURNS TRIGGER AS $T2$
    DECLARE
        x INTEGER;
    BEGIN
        
        IF (NEW.deptnum IS NOT NULL) THEN
            SELECT COUNT(*) INTO x
            FROM DEPTO
            WHERE deptnum = NEW.deptnum;
        
            IF x = 0 THEN
                INSERT INTO DEPTO VALUES (NEW.deptnum, '?????', 'paris', 1);
            END IF;
            
            RETURN NEW;
        ELSE
            RETURN NULL;
        END IF;
        
END;
    
$T2$ LANGUAGE plpgsql;


CREATE TRIGGER trigger2 
BEFORE INSERT 
ON employee FOR EACH ROW
EXECUTE PROCEDURE t2();
