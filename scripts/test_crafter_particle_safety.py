#!/usr/bin/env python3
"""Guard against Particle.FLASH API regressions and repeated crafting completion."""
from pathlib import Path
import unittest

ROOT = Path(__file__).resolve().parents[1]
CRAFTERS = ROOT / "src/main/java/me/apeiros/alchimiavitae/setup/items/crafters"
FLASH_COUNTS = {
    "DivineAltar.java": 2,
    "AltarOfInfusion.java": 3,
    "CosmicCauldron.java": 1,
}


class CrafterParticleSafetyTest(unittest.TestCase):
    def test_flash_requires_color_data(self):
        for filename, expected in FLASH_COUNTS.items():
            with self.subTest(crafter=filename):
                source = (CRAFTERS / filename).read_text(encoding="utf-8")
                particles = [line.strip() for line in source.splitlines()
                             if "spawnParticle(Particle.FLASH," in line]
                self.assertEqual(expected, len(particles))
                for args in particles:
                    self.assertTrue(args.endswith(", Color.WHITE);"), args)

    def test_crafting_task_cancels_before_output(self):
        for filename in FLASH_COUNTS:
            with self.subTest(crafter=filename):
                source = (CRAFTERS / filename).read_text(encoding="utf-8")
                finish = source.split("protected void finish(", 1)[1]
                self.assertEqual(1, finish.count("this.cancel();"))
                self.assertLess(finish.index("this.cancel();"), finish.index("// Output the item"))


if __name__ == "__main__":
    unittest.main()
