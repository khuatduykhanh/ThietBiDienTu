--CREATE TRIGGER NhapHang ON detail_invoice AFTER INSERT AS
--BEGIN
--    update product
--    SET product.quantity = product.quantity + inserted.quantity
--    FROM product
--    JOIN inserted on product.id = inserted.product_id
--END
--GO
CREATE OR REPLACE FUNCTION insert_product_quantity()
RETURNS TRIGGER AS $$
BEGIN
UPDATE product
SET quantity = quantity + NEW.quantity
WHERE id = NEW.product_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_insert_invoice
    AFTER INSERT
    ON detail_invoice
    FOR EACH ROW
    EXECUTE FUNCTION insert_product_quantity();

CREATE OR REPLACE FUNCTION insert_bill_quantity()
RETURNS TRIGGER AS $$
BEGIN
UPDATE product
SET quantity = quantity - NEW.quantity
WHERE id = NEW.product_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER after_insert_bill
    AFTER INSERT
    ON detail_bill
    FOR EACH ROW
    EXECUTE FUNCTION insert_bill_quantity();

CREATE OR REPLACE FUNCTION delete_bill_quantity()
RETURNS TRIGGER AS $$
BEGIN
UPDATE product
SET quantity = quantity + OLD.quantity
WHERE id = OLD.product_id;
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_delete_bill
    AFTER DELETE
    ON detail_bill
    FOR EACH ROW
    EXECUTE FUNCTION delete_bill_quantity();