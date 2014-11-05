-- // migrate_order_data_from_reserve_order_to_orderItem
-- Migration SQL that makes the change goes here.
INSERT INTO orderItems
    SELECT order_id,item_id,coalesce(1) from reserve_order;

-- //@UNDO
-- SQL to undo the change goes here.
UPDATE reserve_order
SET item_id =
(SELECT itemId FROM orderItems
  WHERE reserve_order.order_id=orderItems.orderId);

