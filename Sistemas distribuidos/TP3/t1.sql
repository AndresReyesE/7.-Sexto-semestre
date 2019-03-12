CREATE OR REPLACE FUNCTION t1() RETURNS TRIGGER AS $T1$
    DECLARE
        sysdate CONSTANT VARCHAR := current_date;
    BEGIN
        NEW.datemaj := sysdate;
        RETURN NEW;
END;
    
$T1$ LANGUAGE plpgsql;


CREATE TRIGGER trigger1 
BEFORE UPDATE 
ON employee FOR EACH ROW
EXECUTE PROCEDURE t1();
