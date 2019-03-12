CREATE OR REPLACE FUNCTION t7() RETURNS TRIGGER AS $T7$
    DECLARE
        x INTEGER;
    BEGIN
        
        CASE TG_OP
            WHEN 'INSERT' THEN
                IF (NEW.deptnum IS NOT NULL) THEN
                    SELECT COUNT(*) INTO x
                    FROM DEPTO
                    WHERE deptnum = NEW.deptnum;
                
                    IF x = 0 THEN
                        INSERT INTO DEPTO VALUES (NEW.deptnum, '?????', 'paris', 1);
                    ELSE
                        UPDATE depto SET nemp = nemp + 1 WHERE deptnum = NEW.deptnum;
                    END IF;
                END IF;
            WHEN 'DELETE' THEN
                IF (OLD.deptnum IS NOT NULL) THEN
                    UPDATE depto SET nemp = nemp - 1 WHERE deptnum = OLD.deptnum;
                END IF;   
            ELSE
                IF ((OLD.deptnum <> NEW.deptnum) AND (NEW.deptnum IS NOT NULL)) THEN
                    UPDATE depto SET nemp = nemp - 1 WHERE deptnum = OLD.deptnum;
                    UPDATE depto SET nemp = nemp + 1 WHERE deptnum = NEW.deptnum;
                END IF;
        END CASE;
        
        RETURN NULL;
END;
    
$T7$ LANGUAGE plpgsql;


CREATE TRIGGER trigger7 
AFTER INSERT OR DELETE OR UPDATE of deptnum 
ON employee
FOR EACH ROW
EXECUTE PROCEDURE t7();
