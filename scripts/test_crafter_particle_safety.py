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


class ReleaseSourceSafetyTest(unittest.TestCase):
    def test_tag_publication_refuses_unmerged_source_and_existing_release(self):
        workflow = (ROOT / ".github/workflows/build-and-release.yml").read_text(encoding="utf-8")
        release = workflow.split("name: Publish only a new immutable tagged release", 1)[1]
        required = (
            "if: startsWith(github.ref, 'refs/tags/v')",
            'test "${TAG}" = "v${LEGACY_ARTIFACT_VERSION}"',
            'git fetch --no-tags origin "+refs/heads/master:refs/remotes/origin/master"',
            'MASTER_SHA="$(git rev-parse refs/remotes/origin/master)"',
            'CHECKOUT_SHA="$(git rev-parse HEAD)"',
            'test "$CHECKOUT_SHA" = "$MASTER_SHA" || {',
            'if gh release view "${TAG}"',
            'gh release create "${TAG}" "${JAR}"',
        )
        for clause in required:
            with self.subTest(clause=clause):
                self.assertIn(clause, release)
        self.assertLess(release.index("CHECKOUT_SHA="), release.index("gh release create"))
        self.assertLess(release.index("if gh release view"), release.index("gh release create"))
        self.assertNotIn("--clobber", release)


if __name__ == "__main__":
    unittest.main()
