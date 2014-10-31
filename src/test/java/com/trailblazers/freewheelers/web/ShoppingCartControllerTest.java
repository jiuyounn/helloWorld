package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.ReserveOrder;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.ItemService;
import com.trailblazers.freewheelers.service.ReserveOrderService;
import org.eclipse.jetty.server.session.HashedSession;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import sun.security.acl.PrincipalImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartControllerTest {

    @Mock
    private ReserveOrderService reserveOrderService;
    @Mock
    private AccountService accountService;
    @Mock
    private ItemService itemService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @Test
    public void shouldSaveItemIntoSessionWhenReserveItemIsCalled (){
        Item item = new Item();
        item.setItemId(739L);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        MockHttpSession httpSession = new MockHttpSession();
        ExtendedModelMap expectedModelMap = new ExtendedModelMap();

        when(itemService.getById(739L)).thenReturn(item);
        when(httpServletRequest.getSession()).thenReturn(httpSession);

        shoppingCartController.reserveItem(expectedModelMap, item, httpServletRequest);

        assertTrue(expectedModelMap.containsValue(item));
        assertThat(httpServletRequest.getSession().getAttribute("sessionItem"), is((Object)item));

    }

    @Test
    public void shouldCallReserveOrderServiceWhenCheckoutItemIsCalled(){
        Model model = mock(Model.class);
        Principal principle = new PrincipalImpl("UserCat");
        Item item = new Item();
        item.setItemId(739l);
        item.setQuantity(2l);
        Account account = new Account();
        account.setAccount_id(2l);
        ReserveOrder reserveOrder = new ReserveOrder(2l, 739l, new Date());
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession httpSession = new MockHttpSession();
        HttpSession spy = spy(httpSession);
        spy.setAttribute("sessionItem", item);
        when(httpServletRequest.getSession()).thenReturn(spy);
        when(itemService.getById(739l)).thenReturn(item);
        when(accountService.getAccountByName("UserCat")).thenReturn(account);
        when(itemService.checkItemsQuantityIsMoreThanZero(739l)).thenReturn(2l);

        shoppingCartController.checkoutItem(model, principle, item, httpServletRequest);

        verify(reserveOrderService, times(1)).save(reserveOrder);
        verify(itemService, times(1)).decreaseQuantityByOne(item);
        assertThat(httpServletRequest.getSession().getAttribute("sessionItem"), is(nullValue()));
    }

    @Test
    public void shouldClearItemFromSessionWhenClearCartIsClicked (){
        Item item = new Item();
        item.setItemId(739L);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession httpSession = new MockHttpSession();
        HttpSession spy = spy(httpSession);
        spy.setAttribute("sessionItem", item);

        when(httpServletRequest.getSession()).thenReturn(spy);

        String redirect = shoppingCartController.clearShoppingCart(httpServletRequest);

        assertThat(httpServletRequest.getSession().getAttribute("sessionItem"), is(nullValue()));
        assertEquals(redirect, "redirect:/");
    }

    @Test
    public void shouldCheckIfItemIsAvailableBeforeSavingOrder() {
        Item item = new Item().setItemId(2l);
        when(accountService.getAccountByName(anyString())).thenReturn(new Account());
        when(itemService.getById(2l)).thenReturn(new Item());
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(new MockHttpSession());

        shoppingCartController.checkoutItem(mock(Model.class), mock(Principal.class), item, request);

        verify(itemService).checkItemsQuantityIsMoreThanZero(2l);
    }

    @Test
    public void shouldReturnAErrorMessageWhenTheItemHaveLessThenZeroQuantity() {
        Item item = new Item().setItemId(2l);
        ExtendedModelMap expectedModelMap = new ExtendedModelMap();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(new MockHttpSession());
        when(accountService.getAccountByName(anyString())).thenReturn(new Account());
        when(itemService.getById(2l)).thenReturn(new Item());
        when(itemService.checkItemsQuantityIsMoreThanZero(2l)).thenReturn(0l);

        shoppingCartController.checkoutItem(expectedModelMap, mock(Principal.class), item, request);

        assertThat((String) expectedModelMap.asMap().get("quantityErrorMessage"), is("Sorry, item is no longer available."));
    }

}