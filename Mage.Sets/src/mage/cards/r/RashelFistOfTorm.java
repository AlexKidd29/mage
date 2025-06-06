package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RashelFistOfTorm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.AURA, "Auras");

    public RashelFistOfTorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Auras you control have exalted.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ExaltedAbility(), Duration.WhileOnBattlefield, filter
        )));
    }

    private RashelFistOfTorm(final RashelFistOfTorm card) {
        super(card);
    }

    @Override
    public RashelFistOfTorm copy() {
        return new RashelFistOfTorm(this);
    }
}
