package com.mediterranean.logistics;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShipmentController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {

        model.addAttribute("name", principal.getFirstAttribute("firstName"));
        model.addAttribute("email", principal.getName());

        model.addAttribute("shipments", List.of(
                new Shipment("SHP-2024-001", "Warehouse A", "Distribution Center B", "In Transit"),
                new Shipment("SHP-2024-002", "Distribution Center B", "Warehouse C", "Delivered"),
                new Shipment("SHP-2024-003", "Warehouse C", "Warehouse A", "Customs Hold")
        ));

        return "home";
    }

    record Shipment(String id, String origin, String destination, String status) {}
}
