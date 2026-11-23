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
        return "home";
    }

    @GetMapping("/tracking")
    public String tracking(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
        model.addAttribute("name", principal.getFirstAttribute("firstName"));
        model.addAttribute("shipments", List.of(
                new Shipment("SHP-2024-001", "Warehouse A", "Distribution Center B", "In Transit"),
                new Shipment("SHP-2024-002", "Distribution Center B", "Warehouse C", "Delivered"),
                new Shipment("SHP-2024-003", "Warehouse C", "Warehouse A", "Customs Hold")
        ));
        return "tracking";
    }

    @GetMapping("/costs")
    public String costs(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
        model.addAttribute("name", principal.getFirstAttribute("firstName"));
        model.addAttribute("costs", List.of(
                new CostReport("COST-2024-Q3", "Q3 Shipping Costs", 234500.00),
                new CostReport("COST-2024-Q4", "Q4 Shipping Costs", 198750.00),
                new CostReport("COST-2024-WH", "Warehouse Operations", 156300.00)
        ));
        return "costs";
    }

    @GetMapping("/shared-documents")
    public String sharedDocuments(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
        model.addAttribute("name", principal.getFirstAttribute("firstName"));
        model.addAttribute("documents", List.of(
                new SharedDocument("DOC-2024-001", "Customs Declaration Form", "PDF"),
                new SharedDocument("DOC-2024-002", "Bill of Lading Template", "PDF"),
                new SharedDocument("DOC-2024-003", "Partner Agreement Draft", "DOCX")
        ));
        return "shared-documents";
    }

    record Shipment(String id, String origin, String destination, String status) {}
    record CostReport(String id, String description, double amount) {}
    record SharedDocument(String id, String name, String format) {}
}
