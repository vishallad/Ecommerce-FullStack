package org.example.paymentservice.servicies;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewaySelectorImpl implements PaymentGatewaySelector {

    StripeGateway stripeGateway;
    RazorPayGateway razorPayGateway;

    public PaymentGatewaySelectorImpl(StripeGateway stripeGateway, RazorPayGateway razorPayGateway) {
        this.stripeGateway = stripeGateway;
        this.razorPayGateway = razorPayGateway;
    }

    @Override
    public PaymentGateway get() {
        return stripeGateway;
    }
}
